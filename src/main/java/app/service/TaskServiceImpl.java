package app.service;

import app.commons.JwtTokenUtils;
import app.commons.RedisUtil;
import app.controller.LoginController;
import app.pojo.Attend;
import app.pojo.AttendStatus;
import app.pojo.Task;
import app.vo.TaskVO;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import jmms.core.Entities;
import jmms.core.modules.EntityModule;
import leap.core.annotation.Transactional;
import leap.core.transaction.*;
import leap.lang.New;
import leap.orm.dao.Dao;
import leap.orm.query.CriteriaQuery;
import leap.web.api.orm.ModelDeleteExecutor;
import leap.web.exception.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: fanbopeng
 * @Date: 2019/11/3 17:42
 * @Description:
 */

@Service
public class TaskServiceImpl implements TaskService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Dao dao;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TransactionManager transactionManager;

    @Autowired
    private DataSource dataSource;


    @Override
    public List<TaskVO> getTaskList(int page, int size) {
        LOGGER.info("Param: [page:" + page + "],[size:" + size + "]");

        List<Task> list;

        if (page == 1) {
            list = dao.createCriteriaQuery(Task.class).selectExclude("deleted").limit(page, size).list();
        } else {

            list = dao.createCriteriaQuery(Task.class).selectExclude("deleted").limit((page - 1) * size + 1, page * size).list();
        }
        List<TaskVO> listVO = new ArrayList<>();
        list.forEach(task -> {
            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            listVO.add(taskVO);

            throw new RuntimeException();
        });

        return listVO;
    }

    @Override
    public long getTaskCount() {


        Integer countInt =(Integer)  redisUtil.get(RedisUtil.TASK_KEY + "count");

        if (countInt==null){

           long  count= dao.count(Task.class);
           redisUtil.set(RedisUtil.TASK_KEY + "count",count);
           return count;

        }
        Long count = new Long(countInt);
        return count;


    }

    @Override
    public long getTaskCountByStatus(int status) {
        LOGGER.info("Param: [status:" + status + "]");

        return dao.createCriteriaQuery(Task.class).where(New.hashMap("status", status)).count();
    }

    @Override
    public List<TaskVO> getTaskListByStatus(int status, int page, int size) {
        LOGGER.info("Param: [status:" + status + "],[page:" + page + "],[size:" + size + "]");
        List<Task> taskList;
        if (page == 1) {
            taskList = dao.createCriteriaQuery(Task.class).where(New.hashMap("status", status)).limit(page, size).list();
        } else {

            taskList = dao.createCriteriaQuery(Task.class).where(New.hashMap("status", status)).limit((page - 1) * size + 1, page * size).list();
        }

        List<TaskVO> taskVOList = new ArrayList<>();

        taskList.forEach(task -> {

            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            taskVOList.add(taskVO);

        });

        taskVOList.sort(Comparator.comparing(TaskVO::getBeginTimeStart));

        return taskVOList;
    }

    @Override
    public List<TaskVO> getTaskListByCreateUser(String createUser, int page, int size) {
        LOGGER.info("Param: [createUser:" + createUser + "],[page:" + page + "],[size:" + size + "]");
        List<Task> taskList;

     //  redisUtil.del(RedisUtil.TASK_KEY+createUser);

        if (page == 1) {
            taskList = dao.createCriteriaQuery(Task.class).where(New.hashMap("createUser", createUser)).limit(page, size).list();
        } else {

            taskList = dao.createCriteriaQuery(Task.class).where(New.hashMap("createUser", createUser)).limit((page - 1) * size + 1, page * size).list();
        }

        ArrayList<TaskVO> taskVOList = new ArrayList<>();

        taskList.forEach(task -> {

            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            taskVOList.add(taskVO);

        });

            redisUtil.lSet(RedisUtil.TASK_KEY+createUser,taskVOList);
            taskVOList.sort(Comparator.comparing(TaskVO::getBeginTimeStart));

            return  taskVOList;

    }

    @Override
    public void addTask(Task task) {

        task.setStatus(-1);

        if (dao.insert(Task.class, task) != 1) {

            LOGGER.error("insert task failed");

        }
    }

    @Override
    @Transactional
    public void updateTaskStatus() {

        //status -1 未开始 0 正在进行  1打卡结束
        //先查-1 然后当前时间与开始时间做对比。 再与 结束时间做对比
        // 再查 0   当前时间与结束时间做对比  1


        List<Task> taskListZero = dao.createCriteriaQuery(Task.class).where(New.hashMap("status", -1)).list();

        List<Task> listZero = taskListZero.stream().filter(task -> task.getBeginTimeStart().compareTo(new Timestamp(System.currentTimeMillis())) < 0).collect(Collectors.toList());

        listZero.forEach((task -> {
            task.setStatus(0);
            redisUtil.del(task.getTaskName());

        }));

        dao.batchUpdate(Task.class, listZero);
        //dao.batchUpdate(listZero);

        LOGGER.info("update table task  status 0 success");

        List<Task> taskListOne = dao.createCriteriaQuery(Task.class).where(New.hashMap("status", 0)).list();

        List<Task> listOne = taskListZero.stream().filter(task -> task.getEndTimeOver().compareTo(new Timestamp(System.currentTimeMillis())) < 0).collect(Collectors.toList());
        listOne.forEach((task -> {
            task.setStatus(1);
            redisUtil.del(task.getTaskName());
        }));

        //dao.batchUpdate(listZero);
        dao.batchUpdate(Task.class, listZero);

        LOGGER.info("update table task  status 1 success");

    }

    @Override
    public List<TaskVO> getTaskListByAddress(String address, Integer page, Integer size) {
        LOGGER.info("Param: [createUser:" + address + "],[page:" + page + "],[size:" + size + "]");
        List<Task> taskList;

        if (page == 1) {
            taskList = dao.createCriteriaQuery(Task.class).where(New.hashMap("createUser", address)).limit(page, size).list();
        } else {

            taskList = dao.createCriteriaQuery(Task.class).where(New.hashMap("createUser", address)).limit((page - 1) * size + 1, page * size).list();
        }

        List<TaskVO> taskVOList = new ArrayList<>();

        taskList.forEach(task -> {

            TaskVO taskVO = new TaskVO();
            BeanUtils.copyProperties(task, taskVO);
            taskVOList.add(taskVO);

        });

        taskVOList.sort(Comparator.comparing(TaskVO::getBeginTimeStart));

        return taskVOList;
    }

    @Override
    public void createTask(TaskVO taskVO) {


        Task task = new Task();

        int taskLength = dao.createCriteriaQuery(Task.class).where(New.hashMap("task_name", taskVO.getTaskName())).list().size();

        if (taskLength>0){

            throw new ResponseException(511, "任务已存在");
        }

        BeanUtils.copyProperties(taskVO, task);
        task.setStatus(-1);

        dao.insert(task);
        redisUtil.del(RedisUtil.TASK_KEY+"count");

       if (dao.insert(task)!=1){

           LOGGER.error("insert table failed");

           throw new ResponseException(512, "插入数据库失败");
       }




    }

    @Override
    public TaskVO getTaskByName(String taskName) {

        TaskVO taskVO = (TaskVO) redisUtil.get(RedisUtil.TASK_KEY + taskName);
        if (taskVO==null){

             taskVO =new TaskVO();
          Task task=  dao.createCriteriaQuery(Task.class).where(New.hashMap("task_name", taskName)).list().get(0);

          BeanUtils.copyProperties(task, taskVO);

          redisUtil.set(RedisUtil.TASK_KEY+taskName, taskVO);

          return taskVO;
        }

        return taskVO;


    }

    @Override
    public void deleteTaskByName(String taskName) {

        //删除 task attend attendstatus  redis中对应的 task记录

        List<Task> taskList = dao.createSqlQuery(Task.class, "select * from task where task_name ='" + taskName + "'").list();

        if (taskList.size()==0){

          throw new ResponseException(522,"当前任务不存在");
      }

        Task task = taskList.get(0);



        TransactionProvider provider = transactionManager.getProvider(dataSource);

        SimpleTransactionDefinition simpleTransactionDefinition = new SimpleTransactionDefinition() ;
        simpleTransactionDefinition.setIsolation(TransactionDefinition.Isolation.REPEATABLE_READ);

        Transactions transactions = transactionManager.beginTransactionsAll(simpleTransactionDefinition);
        try {


            dao.delete(task);
            LOGGER.info("delete table task success");

            List<Attend> attendList = dao.createSqlQuery(Attend.class, "select * from attend where task_id=" + task.getId() + "").list();
            List<Integer> taskIdList = attendList.stream().map(Attend::getId).collect(Collectors.toList());
            int[] ids = dao.batchDelete(Attend.class, taskIdList);
            LOGGER.info("delete table attend success");

            List<AttendStatus> attendStatusList= dao.createSqlQuery(AttendStatus.class, "select * from attend_status where task_id=" + task.getId() + "").list();
            List<Integer> taskIdAList = attendStatusList.stream().map(AttendStatus::getAsid).collect(Collectors.toList());
            int[] idsA = dao.batchDelete(AttendStatus.class, taskIdAList);

            LOGGER.info("delete table attend_status success");
            transactions.completeAll();
        }catch (Exception e){

            transactions.setRollbackAllOnly();
            LOGGER.error("delete table failed ,tx callback");
        }

        redisUtil.del(RedisUtil.TASK_KEY+taskName);

    }


}
