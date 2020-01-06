package app.service;

import app.pojo.Attend;
import app.pojo.Employee;
import app.pojo.Task;
import app.vo.AttendVO;
import app.vo.TaskVO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import jmms.core.Entities;
import jmms.core.modules.EntityModule;
import leap.core.value.Record;
import leap.lang.Collections2;
import leap.lang.New;
import leap.orm.dao.Dao;
import leap.web.exception.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: fanbopeng
 * @Date: 2019/11/13 17:01
 * @Description:
 */
@Service
public class AttendServiceImpl implements AttendService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AttendServiceImpl.class);

    @Autowired
    private Dao dao;
    @Autowired
    private Entities entities;


    @Override
    public void createAttend(AttendVO attendVO) {


        Task task = dao.find(Task.class, attendVO.getTid());
        if (task.getStatus() != 0) {
            LOGGER.error("task status is :" + task.getStatus());
            throw new ResponseException(413, "现在不是合适的打卡时间");
        }

        List<Employee> employee = dao.createCriteriaQuery(Employee.class).where(New.hashMap("ename", attendVO.getEmpName())).list();
        if (employee.size() != 1) {
            LOGGER.error("user is not ");
            throw new ResponseException(413, "用户不存在");
        }
        Attend attend = new Attend();
        attend.setTaskid(attendVO.getTid());
        attend.setEmpid(employee.get(0).getEid());
        attend.setAttendAddress(attendVO.getAttendAddress());
        attend.setAttendTime(new Timestamp(System.currentTimeMillis()));

        dao.insert(Attend.class, attend);
        LOGGER.info("insert table attend success");


    }

    @Override
    public List<AttendVO> findAttendByTaskId(int page, int size, Integer taskId) {
        LOGGER.info("Param: [taskId:" + taskId + "],[page:" + page + "],[size:" + size + "]");

        List<Attend> attendList;

        if (page == 1) {
            attendList = dao.createCriteriaQuery(Attend.class).where(New.hashMap("task_id", taskId)).limit(page, size).list();
        } else {

            attendList = dao.createCriteriaQuery(Attend.class).where(New.hashMap("task_id", taskId)).limit((page - 1) * size + 1, page * size).list();
        }
        List<AttendVO> attendVOList = new ArrayList<>();
        attendList.forEach(attend -> {
            AttendVO attendVO = new AttendVO();
            attendVO.setAttendAddress(attend.getAttendAddress());
            attendVO.setAid(attend.getId());
            attendVO.setTid(attend.getTaskid());
            attendVO.setEid(attend.getEmpid());
            attendVO.setAttendTime(attend.getAttendTime());
            attendVO.setEmpName(dao.find(Employee.class, attend.getEmpid()).getEname());
            attendVO.setTaskName(dao.find(Task.class, taskId).getTaskName());

            attendVOList.add(attendVO);
        });

        attendVOList.sort(Comparator.comparing(AttendVO::getAttendTime));

        return attendVOList;
    }

    @Override
    public List<AttendVO> findAttendByAttendUser(Integer page, Integer size, Integer enumber) {
        LOGGER.info("Param: [enumber:" + enumber + "],[page:" + page + "],[size:" + size + "]");

        List<Attend> attendList;

        Employee employee = dao.createCriteriaQuery(Employee.class).where(New.hashMap("enumber", enumber)).list().get(0);
        Integer eid = employee.getEid();
        if (page == 1) {
            attendList = dao.createCriteriaQuery(Attend.class).where(New.hashMap("emp_id", eid)).limit(page, size).list();
        } else {

            attendList = dao.createCriteriaQuery(Attend.class).where(New.hashMap("emp_id", eid)).limit((page - 1) * size + 1, page * size).list();
        }
        List<AttendVO> attendVOList = new ArrayList<>();

        attendList.forEach(attend -> {

            AttendVO attendVO = new AttendVO();

            attendVO.setAid(attend.getId());
            attendVO.setEmpName(employee.getEname());
            attendVO.setEid(eid);
            attendVO.setTid(attend.getTaskid());
            attendVO.setTaskName(dao.find(Task.class, attend.getTaskid()).getTaskName());
            attendVO.setAttendAddress(attend.getAttendAddress());
            attendVO.setAttendTime(attend.getAttendTime());
            attendVO.setEnumber(enumber);

            attendVOList.add(attendVO);
        });

        attendVOList.sort(Comparator.comparing(AttendVO::getAttendTime));

        return attendVOList;
    }

    @Override
    public List<Employee> getNotAttendUser(Integer taskId) {

        List<Employee> employeeList = dao.createCriteriaQuery(Employee.class)
                //写sql的查询方式必须指定所有的字段？
                .fromSqlView("select * from employee where eid not  in " +
                        "(select emp_id from attend where task_id =" + String.valueOf(taskId) + ")").list();

        return employeeList;
    }

    @Override
    public Long getTaskAccountCount(Integer taskId) {

        return dao.createCriteriaQuery(Attend.class).select("emp_id").where(New.hashMap("task_id", taskId)).distinct().count();
    }

    @Override
    public List<Employee> getAttendUserByTime(Integer taskId, Timestamp beginTime, Timestamp endTime) {


        List<Employee> employeeList = dao.createCriteriaQuery(Employee.class)
                .fromSqlView("select * from employee where eid in" +
                        "(select emp_id from attend where attend_time between  '" + beginTime + " ' and ' " + endTime + " ' " +
                        "     and task_id = " + taskId + "  )").list();


        return employeeList;

    }


    public List<Attend> getAttendUser(Integer taskId) {

        //并且不是异地

        Task task = dao.find(Task.class, taskId);

        List<Attend> attendList = dao.createCriteriaQuery(Attend.class)
                //写sql的查询方式必须指定所有的字段？
                /* .fromSqlView("select * from attend where task_id =" + String.valueOf(taskId) +
                         "  and attend_address = '" + task.getAddress() + "' ) t").list();*/
                .where(New.hashMap("task_id", taskId, "attend_address", task.getAddress())).list();
        return attendList;

    }

    /***
     * @Description //过滤响应打卡次数的用户Id
     * @Param [attendUserList, number]
     * @return java.util.List<app.pojo.Employee>
     **/
    @Override
    public List<Integer> getAttendNumber(List<Attend> attendUserList, int number) {

        List<Integer> empIdList = attendUserList.stream().map(Attend::getEmpid).collect(Collectors.toList());

        List<Integer> resEmpIdList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        empIdList.forEach(empId -> {
            if (map.containsKey(empId)) {
                map.put(empId, map.get(empId) + 1);
            } else {
                map.put(empId, 1);
            }
        });
        map.forEach((k, v) -> {
            if (v == number) {

                resEmpIdList.add(k);
            }

        });

        return resEmpIdList;
    }

    @Override
    public List<Integer> getBeginAttendUser(List<Integer> empIdList, Integer taskId) {

        List<Integer> copyEmpIdList = new ArrayList<>(empIdList.size());

        copyEmpIdList.addAll(empIdList);

        Task task = dao.find(Task.class, taskId);


        if (Collections2.isNotEmpty(copyEmpIdList)) {


            Timestamp beginTimeStart = task.getBeginTimeStart();

            Timestamp beginTimeOver = task.getBeginTimeOver();

            List<Attend> list = dao.createSqlQuery(Attend.class, "select * from attend where attend_time between  '" + beginTimeStart + " ' and ' " + beginTimeOver + " ' " +
                    "and task_id = " + String.valueOf(taskId) + " ) a")
                    .list();


            copyEmpIdList.retainAll(list.stream().map(Attend::getEmpid).collect(Collectors.toList()));

            return copyEmpIdList;

        }


        return Collections.emptyList();


    }


    @Override
    public List<Integer> getEndAttendUser(List<Integer> empIdList, Integer taskId) {

        List<Integer> copyEmpIdList = new ArrayList<>(empIdList.size());

        copyEmpIdList.addAll(empIdList);

        Task task = dao.find(Task.class, taskId);

        if (Collections2.isNotEmpty(copyEmpIdList)) {

            List<Attend> list = dao.createSqlQuery(Attend.class, "select * from attend where attend_time between  '" + task.getEndTimeStart() + " ' and ' " + task.getEndTimeOver() + " ' " +
                    "and task_id = " + String.valueOf(taskId) + " ) a")
                    .list();

            copyEmpIdList.retainAll(list.stream().map(Attend::getEmpid).collect(Collectors.toList()));

            return copyEmpIdList;

        }

        return Collections.emptyList();
    }

    @Override
    public List<Integer> getAllopatric(Integer taskId) {
        Task task = dao.find(Task.class, taskId);

        List<Attend> attendList = dao.createSqlQuery(Attend.class, "select emp_id  from attend WHERE task_id = " + taskId + " and attend_address != '" + task.getAddress() + "'").list();

        return attendList.stream().map(Attend::getEmpid).collect(Collectors.toList());
    }

    @Override
    public List<Employee> findnormalEmpList(List<Integer> allEmpId) {

        List<Employee> employeeList = new ArrayList<>();
        if (allEmpId.size() == 1) {

            Employee employee = dao.find(Employee.class, allEmpId.get(0));

            employeeList.add(employee);

        } else {

            employeeList = dao.findList(Employee.class, allEmpId.toArray());
        }


        return employeeList;
    }
}