package app.intercepter;

import app.intercepter.AttendInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author: fanbopeng
 * @Date: 2019/11/8 13:53
 * @Description:  拦截器注册
 */
@Configuration
public class AttendWebMvcConfiguerAdapter extends WebMvcConfigurerAdapter {




    @Override
    public void addInterceptors(InterceptorRegistry registry){
        
        registry.addInterceptor(new AttendInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/login","/swagger-ui.html");
        super.addInterceptors(registry);
    }

    @Bean
    public AttendInterceptor attendInterceptor(){


        return  new AttendInterceptor();
    }

}
