package app.service;

import app.vo.AttendStatusVO;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: fanbopeng
 * @Date: 2019/11/15 13:30
 * @Description:
 */
public interface AttendStatusService {
    /***

     * @Param [taskId]
     * @return java.sql.Timestamp
     **/
    Timestamp getTaskEndTimeByTaskId(Integer taskId);

    /***

     * @Param [taskId]
     * @return void
     **/
    void insertAttendStatus(Integer taskId);


    /***

     * @Param [taskid]
     * @return java.util.List<app.vo.AttendStatusVO>
     **/
    List<AttendStatusVO> getAttendStatusByTaskId(Integer taskid);
}
