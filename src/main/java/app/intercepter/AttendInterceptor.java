package app.intercepter;

import app.commons.JwtTokenUtils;
import app.commons.RedisUtil;
import app.controller.LoginController;
import leap.core.annotation.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author: fanbopeng
 * @Date: 2019/11/8 13:56
 * @Description: 拦截器配置
 */

public class AttendInterceptor implements HandlerInterceptor {



    protected static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {


        String requestURI = httpServletRequest.getRequestURI();

        String accessToken = httpServletRequest.getHeader("access_token");

        BeanFactory beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
        RedisUtil redisUtil = (RedisUtil) beanFactory.getBean("redisUtil");


        if (redisUtil.get(RedisUtil.TOKEN_KEY+accessToken)==null){
            return false;
        }
        LOGGER.info("request"+requestURI+"validate pass");
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
