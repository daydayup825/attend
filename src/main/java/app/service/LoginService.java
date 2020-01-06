package app.service;

import app.pojo.Attend;
import app.pojo.Employee;
import leap.core.value.Record;

/**
 * @author: fanbopeng
 * @Date: 2019/11/6 22:11
 * @Description:
 */
public interface LoginService {


    /**
     * @Param [username]
     * @return leap.core.value.Record
     **/
    Employee findUserByUserEnumber(Integer enumber);


    /***
     * @Param [username, password]
     * @return java.lang.String
     **/
    String login(Integer enumber, String password);
}
