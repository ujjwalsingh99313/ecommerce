package com.ujjawal.ecommerce.config;

import com.ujjawal.ecommerce.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*This annotation indicates that the class is a configuration class and will be used to configure Spring beans.*/
@Configuration

/* This injects an instance of Google0Auth2SuccessHandler, which is presumably a custom success handler for Google OAuth2 authentication.*/
@EnableWebSecurity

/*This class extends WebSecurityConfigureAdapter, which is a convenient base class for configuring Spring Security for web applications.*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    /* This injects an instance of Google0Auth2SuccessHandler, which is presumably a custom success handler for Google OAuth2 authentication*/
    Google0Auth2SuccessHandler google0Auth2SuccessHandler;
    @Autowired
    /*This injects an instance of CustomUserDetailService, which is a custom implementation of Spring Security's UserDetailsService interface for loading user-specific data.*/
    CustomUserDetailService customUserDetailService;
    @Override
    /*configure(HttpSecurity http): This method configures web-based security.*/
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                /*Allows access to certain paths without authentication.*/
                .antMatchers("/", "/shop/**", "/register", "/h2-console/**").permitAll()
                /*Specifies that paths under "/admin" require users to have the "ADMIN" role.*/
                .antMatchers("/admin/**").hasRole("ADMIN")
                /*Specifies that any other request requires authentication.*/
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error= true")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                ////gor google login////////
                .oauth2Login()
                .loginPage("/login")
                .successHandler(google0Auth2SuccessHandler)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .and()
                .csrf()
                .disable() ;

        http.headers().frameOptions().disable();


    }
    /*Declares a bean named bCryptPasswordEncoder that returns an instance of BCryptPasswordEncoder. This bean is used for password encoding*/
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*This method configures authentication manager builder to use the custom user details service for authentication.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService);

    /*This method configures web-based security settings, specifically ignoring certain resources to be accessible without authentication, such as CSS, JavaScript, and images*/
    }
    @Override
    public void  configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/resources/**" ,"/static/**","/images/**","/productImages/**","/css/**","/js/**");
    }
}

