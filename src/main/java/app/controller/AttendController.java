package app.controller;

import app.commons.RedisUtil;
import app.commons.ResponseData;
import app.commons.ResponseUtil;
import app.pojo.Employee;
import app.service.AttendService;
import app.vo.AttendVO;
import app.vo.TimeVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import leap.web.exception.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fanbopeng
 * @Date: 2019/11/12 19:34
 * @Description:
 */
@RestController
@RequestMapping("/attend")
public class AttendController {


    @Autowired
    private AttendService attendService;




    @ApiOperation(value = "创建打卡记录")
    @PostMapping("/attend")
    public ResponseData createAttend(@RequestBody AttendVO attendVO) {

        if (attendVO == null) {
            throw new ResponseException(410, "请求参数非法");
        }
        attendService.createAttend(attendVO);
        return new ResponseUtil().setMsg("success");

    }

    @ApiOperation(value = "根据任务展示打卡记录,并按照打卡的时间进行排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "taskid", value = "任务名称所对应的id", required = true, dataType = "Integer")
    })
    @GetMapping("/taskAttend/{page}/{size}/{taskId}")
    public ResponseData getAttendByTaskId(@PathVariable Integer page, @PathVariable Integer size, @PathVariable Integer taskId) {


        if (taskId == null) {

            throw new ResponseException(410, "请求参数非法");
        }
        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }
        List<AttendVO> list = attendService.findAttendByTaskId(page, size, taskId);

        return new ResponseUtil<>().setData(list);

    }


    @ApiOperation(value = "查看某位员工的打卡记录,并按照打卡的时间进行排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "attendUser", value = "任务名称所对应的id", required = true, dataType = "String")
    })
    @GetMapping("userAttend/{enumber}/{page}/{size}")
    public ResponseData getAttendByTaskAttendUser(@PathVariable Integer enumber, @PathVariable Integer page, @PathVariable Integer size) {

        if (enumber == null) {

            throw new ResponseException(410, "请求参数非法");
        }
        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }
        List<AttendVO> list = attendService.findAttendByAttendUser(page, size, enumber);

        return new ResponseUtil<>().setData(list);

    }

    @ApiOperation(value = "查看当前任务任然没有打卡的员工，以及未打卡员工总人数")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer")
    @GetMapping("taskNotAttend/{taskId}")
    public ResponseData getNotAttendUser(@PathVariable Integer taskId) {




        if (taskId == null) {

            throw new ResponseException(410, "请求参数非法");
        }

        List<Employee> employeeList = attendService.getNotAttendUser(taskId);

        Map<String, Object> map = new HashMap<>();
        map.put("employeeList", employeeList);
        map.put("count", employeeList.size());
        return new ResponseUtil<>().setData(map);

    }

    @ApiOperation(value = "统计某个任务的打卡总人数")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer")
    @GetMapping("taskAccountCount/{taskId}")
    public ResponseData getTaskAccountCount(@PathVariable Integer taskId) {

        if (taskId == null) {

            throw new ResponseException(410, "请求参数非法");
        }

        Long count = attendService.getTaskAccountCount(taskId);

        return new ResponseUtil<>().setData(count);

    }

    @ApiOperation(value = "统计某一时间段打卡人数,以及总人数")
    @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "Integer")
    @GetMapping("getAttendUserByTime/{taskId}")
    public ResponseData getAttendUserByTime(@PathVariable Integer taskId, @RequestBody TimeVo timeVo) {

        if (taskId == null || timeVo == null) {

            throw new ResponseException(410, "请求参数非法");
        }



        Timestamp beginTime = timeVo.getBeginTime();
        Timestamp endTime = timeVo.getEndTime();

        List<Employee> employeeList = attendService.getAttendUserByTime(taskId, beginTime, endTime);

        Map<String, Object> map = new HashMap<>();

        map.put("employeeList", employeeList);
        map.put("count", employeeList.size());

        return new ResponseUtil<>().setData(map);

    }


}
