package app.service;

import app.pojo.Attend;
import app.pojo.Employee;
import app.vo.AttendVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: fanbopeng
 * @Date: 2019/11/13 17:01
 * @Description:
 */
public interface AttendService {

    /**
     * @Param [attendVO]
     * @return void
     */
    void createAttend(AttendVO attendVO);

    /***
     * @Param [page, size, taskName]
     * @return java.util.List<app.vo.AttendVO>
     **/
    List<AttendVO> findAttendByTaskId(int page, int size, Integer taskName);

    /***
     * @Param [page, size, attendUser]
     * @return java.util.List<app.vo.AttendVO>
     **/
    List<AttendVO> findAttendByAttendUser(Integer page, Integer size, Integer attendUser);

    /**
     * @Param [taskId]
     * @return java.util.List<app.pojo.Employee>
     **/
    List<Employee> getNotAttendUser(Integer taskId);

    /***
     * @Param [taskId]
     * @return java.lang.Long
     **/
    Long getTaskAccountCount(Integer taskId);

    /**
     * @Param [taskId, beginTime, endTime]
     * @return java.util.List<app.pojo.Employee>
     **/
    List<Employee> getAttendUserByTime(Integer taskId, Timestamp beginTime, Timestamp endTime);

    /***
     * @Param [attendUserList, number]
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getAttendNumber(List<Attend> attendUserList, int number);

    /***
     * @Param [empIdList, taskId]
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getBeginAttendUser(List<Integer> empIdList, Integer taskId);

    /***
     * @Param [empIdList, taskId]
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getEndAttendUser(List<Integer> empIdList, Integer taskId);

    /***
     * @Param [taskId]
     * @return java.util.List<java.lang.Integer>
     **/
    List<Integer> getAllopatric(Integer taskId);

    /***
     * @Param [allEmpId]
     * @return java.util.List<app.pojo.Employee>
     **/
    List<Employee> findnormalEmpList(List<Integer> allEmpId);
}


