package app.service;

import app.pojo.Attend;
import app.pojo.Task;
import app.vo.TaskVO;

import java.util.List;

/**
 * @author: fanbopeng
 * @Date: 2019/11/8 21:06
 * @Description:
 */
public interface TaskService {

    /***
     * @Param [page, size]
     * @return java.util.List<app.vo.TaskVO>
     **/
    List<TaskVO> getTaskList(int page, int size);

    /***
     * @Param []
     * @return long
     **/
    long getTaskCount();

    /***
     * @Param [status]
     * @return long
     **/
    long getTaskCountByStatus(int status);

    /***
     * @Param [status, page, size]
     * @return java.util.List<app.vo.TaskVO>
     **/
    List<TaskVO> getTaskListByStatus(int status, int page, int size);

    /***
     * @Param [createUser, page, size]
     * @return java.util.List<app.vo.TaskVO>
     **/
    List<TaskVO> getTaskListByCreateUser(String createUser, int page, int size);

    /***
     * @Param [task]
     * @return void
     **/
    void addTask(Task task);

    /***
     * @Param []
     * @return void
     **/
    void updateTaskStatus();

    /***
     * @Param [address, page, size]
     * @return java.util.List<app.vo.TaskVO>
     **/
    List<TaskVO> getTaskListByAddress(String address, Integer page, Integer size);


    void createTask(TaskVO taskVO);


    TaskVO getTaskByName(String taskName);

    void deleteTaskByName(String taskName);
}
