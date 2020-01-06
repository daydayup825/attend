package app.controller;

import app.commons.ResponseData;
import app.commons.ResponseUtil;
import app.pojo.Attend;
import app.service.AttendStatusService;
import app.vo.AttendStatusVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import leap.web.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: fanbopeng
 * @Date: 2019/11/15 11:10
 * @Description:  打卡状态控制层
 */
@RestController
@RequestMapping("/attendStatus")
public class AttendStatusController {


    @Autowired
    private AttendStatusService asService;

        @ApiOperation(value = "根据任务id生成打卡结果")
    @ApiImplicitParam(name = "taskid", value = "任务id", required = true, dataType = "Integer")
    @GetMapping("/count/{taskid}")
    public ResponseData  updateAttendStatusByTaskId(@PathVariable Integer taskid){


         Timestamp timestamp =  asService.getTaskEndTimeByTaskId(taskid);

         if (timestamp.compareTo(new Timestamp(System.currentTimeMillis()))>0){

             throw  new ResponseException(522, "当前任务还没结束，不能更新任务打卡状态");

         }
         asService.insertAttendStatus(taskid);

        return new ResponseUtil<>().setMsg("success");


    }


    @ApiOperation(value = "根据任务id获取打卡打卡统计信息")
    @ApiImplicitParam(name = "taskid", value = "任务id", required = true, dataType = "Integer")
    @GetMapping("/attendStatusList/{taskid}")
    public ResponseData  getAttendStatusByTaskId(@PathVariable Integer taskid){


        Timestamp timestamp =  asService.getTaskEndTimeByTaskId(taskid);

        if (timestamp.compareTo(new Timestamp(System.currentTimeMillis()))>0){

            throw  new ResponseException(523, "当前任务还没结束，不能获取任务打卡状态");

        }
          List<AttendStatusVO>  attendStatusVOList=  asService.getAttendStatusByTaskId(taskid);

        return new ResponseUtil<>().setData(attendStatusVOList);


    }




}
