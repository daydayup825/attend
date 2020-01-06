package app.service;

import app.commons.AESUtil;
import app.commons.CookieUtil;
import app.commons.JwtTokenUtils;
import app.commons.RedisUtil;
import app.controller.LoginController;
import app.pojo.Attend;
import app.pojo.Employee;
import jmms.core.Entities;
import jmms.core.modules.EntityModule;
import leap.core.value.Record;
import leap.htpl.ast.Else;
import leap.lang.New;
import leap.orm.dao.Dao;
import leap.web.api.mvc.params.QueryOptions;
import leap.web.api.orm.QueryListResult;
import leap.web.exception.ResponseException;
import lombok.experimental.Tolerate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.jws.Oneway;
import java.util.List;

/**
 * @author: fanbopeng
 * @Date: 2019/11/6 22:12
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Dao dao;

    @Autowired
    private Entities entities;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public Employee findUserByUserEnumber(Integer ename) {



        List<Employee> employeeList = dao.createCriteriaQuery(Employee.class).where(New.hashMap("enumber", ename)).list();

        if (employeeList.size() != 1) {
            throw new ResponseException(400, "用户不存在");
        }
        return  employeeList.get(0);
    }

    @Override
    public String login(Integer enumber, String password) {

        Employee employee = findUserByUserEnumber(enumber);

        String  dbPwd = new AESUtil(employee.getPassword()).decrypt();


        if (!StringUtils.equals(password, dbPwd)) {

            throw new ResponseException(406, "用户名或者密码不正确");

        }
        String token = JwtTokenUtils.builder().msg(employee.getEnumber().toString()).build().creatJwtToken();
        redisUtil.set(RedisUtil.TOKEN_KEY+token, employee.getEnumber());
        //JwtTokenUtils.tokenList.add(token);
        return token;

    }
}
