package app.controller;


import app.commons.ResponseData;
import app.commons.ResponseUtil;
import app.pojo.Task;
import app.service.TaskService;

import app.vo.TaskVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import leap.web.exception.ResponseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: fanbopeng
 * @Date: 2019/11/3 17:39
 * @Description:任务控制层
 */

@RestController
@RequestMapping("/task")
public class TaskController {


    @Autowired
    private TaskService taskService;


    @ApiOperation(value = "获取打卡任务列表", notes = "获取全部列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "int")
    })
    @GetMapping("/list/{page}/{size}")
    public ResponseData getTaskList(@PathVariable int page, @PathVariable int size) {


        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }

        return new ResponseUtil<>().setData(taskService.getTaskList(page, size));

    }


    @ApiOperation(value = "创建打卡任务")
    @PostMapping("/task")
    public ResponseData createTask(@RequestBody TaskVO taskVO) {


        if (taskVO == null) {

            throw new ResponseException(410, "请求参数非法");
        }

        taskService.createTask(taskVO);

        return new ResponseUtil<>().setData("success");
    }


    @ApiOperation(value = "获取打卡任务总数")
    @GetMapping("/count")
    public ResponseData getCount() {

        long count = taskService.getTaskCount();
        return new ResponseUtil<>().setData(count);

    }

    @ApiOperation(value = "根据状态获取打卡任务状态获取总数")
    @ApiImplicitParam(name = "status", value = "-1打卡未开始，1打卡结束,0打卡正在进行", required = true, dataType = "Integer")
    @GetMapping("/count/{status}")
    public ResponseData getCountByStatus(@PathVariable int status) {

        if (status != -1 && status != 1 && status != 0) {

            throw new ResponseException(410, "请求参数非法");
        }

        long count = taskService.getTaskCountByStatus(status);
        return new ResponseUtil<>().setData(count);

    }

    @ApiOperation(value = "根据状态获取打卡任务状态获取总数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "-1打卡未开始，1打卡结束,0打卡正在进行", required = true, dataType = "Integer")
    })
    @GetMapping("/list/{page}/{size}/{status}")
    public ResponseData getTaskListByStatus(@PathVariable int status, @PathVariable int page, @PathVariable int size) {

        if (status != -1 && status != 1 && status != 0) {

            throw new ResponseException(410, "请求参数非法");
        }
        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }

        List<TaskVO> list = taskService.getTaskListByStatus(status, page, size);

        return new ResponseUtil<>().setData(list);

    }

    @ApiOperation(value = "根据地址查询任务记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "createUser", value = "创建人", required = true, dataType = "String")
    })
    @GetMapping("/list/{page}/{size}/{address}")
    public ResponseData getTaskListByAddress(@PathVariable String address, @PathVariable Integer page, @PathVariable Integer size) {

        if (address == null) {

            throw new ResponseException(410, "请求参数非法");
        }
        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }

        List<TaskVO> list = taskService.getTaskListByAddress(address, page, size);

        return new ResponseUtil<>().setData(list);

    }

    @ApiOperation(value = "根据任务创建时间排序查询任务记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "status", value = "-1打卡未开始，1打卡结束,0打卡正在进行", required = true, dataType = "Integer")
    })
    @GetMapping("/orderList/{page}/{size}/{status}")
    public ResponseData getOrderList(@PathVariable int status, @PathVariable int page, @PathVariable int size) {

        if (status != -1 && status != 1 && status != 0) {

            throw new ResponseException(410, "请求参数非法");
        }
        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }

        List<TaskVO> list = taskService.getTaskListByStatus(status, page, size);

        return new ResponseUtil<>().setData(list);

    }

    @ApiOperation(value = "根据任务创建用户查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "每页大小", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "createUser", value = "创建用户", required = true, dataType = "String")
    })
    @GetMapping("/createUserList/{page}/{size}/{createUser}")
    public ResponseData getTaskListByCreateUser(@PathVariable String createUser, @PathVariable int page, @PathVariable int size) {

        if (createUser == null) {

            throw new ResponseException(411, "请求参数非法");
        }

        if (page < 0 || size < 0) {

            throw new ResponseException(410, "请求参数非法");
        }

        List<TaskVO> list = taskService.getTaskListByCreateUser(createUser, page, size);

        return new ResponseUtil<>().setData(list);

    }

    @ApiOperation(value = "新增打卡任务")
    @PostMapping("/addTask")
    public ResponseData addTask(@RequestBody Task task) {

        if (task == null) {
            throw new ResponseException(410, "请求参数非法");
        }
        taskService.addTask(task);

        return new ResponseUtil().setMsg("success");

    }

    @ApiOperation(value = "更新任务状态")
    @PutMapping("/updateStatus")
    public ResponseData updateTaskStatus() {

        taskService.updateTaskStatus();

        return new ResponseUtil().setMsg("success");


    }

    @ApiOperation(value = "通过任务名称获取任务详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskName", value = "任务名称", required = true, dataType = "String"),

    })
    @GetMapping("/getTask/{taskName}")
    public ResponseData getTaskByName(@PathVariable String taskName) {


        if (StringUtils.isEmpty(taskName)) {

            throw new ResponseException(410, "请求参数非法");
        }

        TaskVO taskVO = taskService.getTaskByName(taskName);

        return new ResponseUtil<>().setData(taskVO);

    }

    @ApiOperation(value = "通过任务名称删除任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskName", value = "任务名称", required = true, dataType = "String"),

    })
    @DeleteMapping("/{taskName}")
    public ResponseData deleteTaskByName(@PathVariable String taskName){

        if (StringUtils.isEmpty(taskName)){

            throw new ResponseException(410, "请求参数非法");

        }

        taskService.deleteTaskByName(taskName);

        return  new ResponseUtil<>().setMsg("success");

    }





}
