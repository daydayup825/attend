package app.controller;

import app.commons.*;
import app.service.LoginService;
import app.vo.LoginVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: fanbopeng
 * @Date: 2019/11/6 21:48
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "登录", notes = "用户登录")
    @PostMapping("/login")
    public ResponseData login(@RequestBody LoginVO loginMap) {


        Integer enumber = loginMap.getEnumber();
        String password = loginMap.getPassword();
        LOGGER.info("用户" + enumber + "登录");
        String token = loginService.login(enumber, password);

        return new ResponseUtil<>().setData(token);

    }

    @ApiOperation(value = "登录", notes = "用户登录")
    @DeleteMapping("/logout")
    public ResponseData logout(HttpServletRequest httpServletRequest) {

        String accessToken = httpServletRequest.getHeader("access_token");

        String freeJwt = JwtTokenUtils.builder().build().freeJwt(accessToken);

        redisUtil.del(RedisUtil.TOKEN_KEY + accessToken);

        //JwtTokenUtils.tokenList.remove(accessToken);

        return new ResponseData();
    }

}
