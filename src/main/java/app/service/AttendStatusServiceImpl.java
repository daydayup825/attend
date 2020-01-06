package app.service;


import app.commons.RedisUtil;
import app.controller.LoginController;
import app.pojo.Attend;
import app.pojo.AttendStatus;
import app.pojo.Employee;
import app.pojo.Task;
import app.vo.AttendStatusVO;
import io.swagger.models.auth.In;
import leap.core.annotation.Transactional;
import leap.core.transaction.*;
import leap.lang.New;
import leap.orm.annotation.Id;
import leap.orm.dao.Dao;
import leap.web.exception.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: fanbopeng
 * @Date: 2019/11/15 13:30
 * @Description:
 */
@Service
public class AttendStatusServiceImpl implements AttendStatusService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Dao dao;

    @Autowired
    private  TransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private AttendServiceImpl attendService;

    @Override
    public Timestamp getTaskEndTimeByTaskId(Integer taskId) {

        Task task = dao.find(Task.class, taskId);

        if (null == task) {

            throw new ResponseException(521, "寻找的任务不存在");
        }

        return task.getEndTimeOver();
    }

    @Override
    public void insertAttendStatus(Integer taskId) {


        //先查Attend表，得到 taskid所对应的 attend记录
        //再查询employee表，找到所有的员工，


        //员工id做对应，

        //begin tx
        TransactionProvider provider = transactionManager.getProvider(dataSource);

        SimpleTransactionDefinition simpleTransactionDefinition = new SimpleTransactionDefinition() ;
        simpleTransactionDefinition.setIsolation(TransactionDefinition.Isolation.REPEATABLE_READ);

        Transactions transactions = transactionManager.beginTransactionsAll(simpleTransactionDefinition);

        //缺勤
        List<Employee> notAttendUserList = attendService.getNotAttendUser(taskId);
        List<AttendStatus> absenteeismList = new ArrayList<>();

        notAttendUserList.forEach(employee -> {
                    AttendStatus attendStatus = new AttendStatus();
                    attendStatus.setEmpid(employee.getEid());
                    attendStatus.setTaskid(taskId);
                    //缺勤
                    attendStatus.setStatus(4);

                    absenteeismList.add(attendStatus);
                }
        );




        //只打卡了一次的员工 可能是迟到 也可能是早退
        //查询打了卡的员工都有哪些
        List<Attend> attendUserList = attendService.getAttendUser(taskId);
        //查询每个员工的打卡次数=1
        List<Integer> empIdListOne = attendService.getAttendNumber(attendUserList, 1);

        //获取开始打卡的员工，因为只打了一次卡，所以就是第二次未打卡，早退
        List<Integer> empIdBeginList = attendService.getBeginAttendUser(empIdListOne, taskId);

        List<Employee> employeeBeginList = dao.findList(Employee.class, empIdBeginList.toArray());

        List<AttendStatus> leaveEarlyList = new ArrayList<>();

        employeeBeginList.forEach(employee -> {

            AttendStatus attendStatus = new AttendStatus();

            attendStatus.setEmpid(employee.getEid());
            attendStatus.setTaskid(taskId);
            //早退
            attendStatus.setStatus(2);

            leaveEarlyList.add(attendStatus);

        });


        //获取结束打卡的员工，因为只打了一次卡，所以就是第1次未打卡，迟到
        List<Integer> empIdEndList = attendService.getEndAttendUser(empIdListOne, taskId);

        List<Employee> employeeEndList = dao.findList(Employee.class, empIdEndList.toArray());

        List<AttendStatus> tardyList = new ArrayList<>();

        employeeEndList.forEach(employee -> {

            AttendStatus attendStatus = new AttendStatus();

            attendStatus.setEmpid(employee.getEid());
            attendStatus.setTaskid(taskId);
            //早退
            attendStatus.setStatus(3);

            tardyList.add(attendStatus);

        });


        //异地打卡

        List<Integer> empIdAllopatricList = attendService.getAllopatric(taskId);

        List<Employee> empAllopatricList = dao.findList(Employee.class, empIdAllopatricList.toArray());

        List<AttendStatus> employeeAllopatricList = new ArrayList<>();

        empAllopatricList.forEach(employee -> {

            AttendStatus attendStatus = new AttendStatus();

            attendStatus.setEmpid(employee.getEid());
            attendStatus.setTaskid(taskId);
            //异地
            attendStatus.setStatus(5);

            employeeAllopatricList.add(attendStatus);

        });


        //正常打卡

        List<Integer> allEmpId = dao.createCriteriaQuery(Attend.class).where("task_id", taskId).list().stream().map(Attend::getEmpid).collect(Collectors.toList());

        List<Integer> absEmpId = absenteeismList.stream().map(AttendStatus::getEmpid).collect(Collectors.toList());
        List<Integer> leaEmpId = leaveEarlyList.stream().map(AttendStatus::getEmpid).collect(Collectors.toList());
        List<Integer> tarEmpId = tardyList.stream().map(AttendStatus::getEmpid).collect(Collectors.toList());
        List<Integer> aloEmpId = employeeAllopatricList.stream().map(AttendStatus::getEmpid).collect(Collectors.toList());

        allEmpId.removeAll(absEmpId);
        allEmpId.removeAll(leaEmpId);
        allEmpId.removeAll(tarEmpId);
        allEmpId.removeAll(aloEmpId);
        //对id去重
        allEmpId = new ArrayList<>(new HashSet<>(allEmpId));
        List<Employee> empAllList = attendService.findnormalEmpList(allEmpId);

        List<AttendStatus> employeeAllList = new ArrayList<>();

        empAllList.forEach(employee -> {

            AttendStatus attendStatus = new AttendStatus();

            attendStatus.setEmpid(employee.getEid());
            attendStatus.setTaskid(taskId);
            //正常
            attendStatus.setStatus(1);


            employeeAllList.add(attendStatus);

        });


        try {

            int[] resLeaveEarlyList = dao.batchInsert(leaveEarlyList);

            LOGGER.info("insert table " + resLeaveEarlyList.length + "record");

            int[] resAbsenteeismList = dao.batchInsert(absenteeismList);
            LOGGER.info("insert table " + resAbsenteeismList.length + "record");


            int[] resTardyList = dao.batchInsert(tardyList);
            LOGGER.info("insert table " + resTardyList.length + "record");

            int[] resEmployeeAllopatricList = dao.batchInsert(employeeAllopatricList);
            LOGGER.info("insert table " + resEmployeeAllopatricList.length + "record");

            int[] resEmployeeAllList = dao.batchInsert(employeeAllList);
            LOGGER.info("insert table " + resEmployeeAllList.length + "record");



        }catch (Exception e){

            transactions.setRollbackAllOnly();
            LOGGER.error("insert table  failed ");

            transactions.completeAll();

            throw  new ResponseException(547, "insert table failed");
        }






    }

    @Override
    public List<AttendStatusVO> getAttendStatusByTaskId(Integer taskid) {

        LOGGER.info("params taskid :[" + taskid + "]");

        List<AttendStatusVO> rAttendListVo = (List<AttendStatusVO>)(Object)redisUtil.lGet(RedisUtil.ATTEND_KEY + taskid,0,-1);


        if (rAttendListVo==null) {


            List<AttendStatus> attendStatusList = dao.createSqlQuery(AttendStatus.class, "select * from attend_status where taskid =" + taskid + "").list();

            List<AttendStatusVO> attendStatusVOList = new ArrayList<>();

            attendStatusList.forEach(attendStatus -> {
                AttendStatusVO asVO = new AttendStatusVO();
                Task task = dao.find(Task.class, attendStatus.getTaskid());
                Employee employee = dao.find(Employee.class, attendStatus.getEmpid());

                asVO.setId(attendStatus.getAsid());
                asVO.setTaskId(taskid);
                asVO.setTaskName(task.getTaskName());
                asVO.setStatus(attendStatus.getStatus());
                asVO.setEmpName(employee.getEname());
                asVO.setWorkNumber(employee.getEnumber());

                attendStatusVOList.add(asVO);
            });

                redisUtil.lSet(RedisUtil.ATTEND_KEY+taskid,attendStatusVOList);
                redisUtil.expire(RedisUtil.ATTEND_KEY+taskid,259200);
            return attendStatusVOList;

        }
        return rAttendListVo;
    }




}

