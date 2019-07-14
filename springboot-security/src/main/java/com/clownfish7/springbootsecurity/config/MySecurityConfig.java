package com.clownfish7.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @author You
 * @create 2019-07-13 15:29
 */
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权
        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/pages/**").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3")
                ;

        //开启自动配置的登录功能，效果：如果没有登陆，没有权限就会来到登陆页面 spring自带login
        http.formLogin().loginPage("/login")
                .usernameParameter("user")
                .passwordParameter("pwd");
        //1. /login 来到登陆页面
        //2.重定向到 /login?error 表示登陆失败
        //3.更多详细规定
        //4.默认post形式的 /login代表处理登陆
        //5.一旦定制loginPage 那么loginPage的post请求就是登陆

        //开启自动注销的配置功能
        http.logout();
        //1.访问 /logout 表示用户注销，清空session
        //2.注销成功会返回 /login?logout 页面;

        //开启记住我功能
        http.rememberMe()
//                .rememberMeParameter()
        ;
        //登陆成功以后将cookie发送给浏览器，以后访问页面将带上这个cookie，只要通过检查就可以免登陆
        //注销 删除cookie

    }


    //定义认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and()
                .withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and()
                .withUser("wangwu").password("123456").roles("VIP1","VIP3");
    }

}
