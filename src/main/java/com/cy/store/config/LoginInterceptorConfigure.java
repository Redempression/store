package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理器拦截器的注册
 *
 * 注册过滤器的技术:借助WebMvcConfigure接口将用户定义的拦截器进行注册.
 * 所以想要注册过滤器需要定义一个类使其实现WebMvcConfigure接口并在其内部添加黑名单
 * (在用户登录的状态下才可以访问的页面资源)和
 * 白名单(哪些资源可以在不登录的情况下访问:①register.html②login.html③index.html
 * ④/users/reg⑤/users/login⑥静态资源)。
 * @author 魏敏捷
 * @version 1.0
 */

@Configuration
public class LoginInterceptorConfigure implements WebMvcConfigurer {

    /** 配置拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义拦截器对象
        HandlerInterceptor interceptor = new LoginInterceptor();

        //配置白名单，需存放在List集合中
        List<String> patterns =  new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/distracts/**");
        patterns.add("/products/**");

        //完成拦截器的注册
        registry.addInterceptor(interceptor)//向注册器中把自定义拦截器添加进去即注册拦截器
                .addPathPatterns("/**") //表示要拦截的url是什么
                .excludePathPatterns(patterns); //表示哪些是白名单

    }
}
