package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // http 요청에 대한 보안
        http.formLogin().
                loginPage("/login").
                defaultSuccessUrl("/").
                usernameParameter("email").
                failureUrl("/login/error").
                and().
                logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                logoutSuccessUrl("/");

        http.authorizeRequests().
                mvcMatchers("/", "/members/**", "/item/**", "/images/**", "/login", "/login/**", "/logout", "/logout/**").permitAll().
                mvcMatchers("/admin/**").hasRole("ADMIN").
                antMatchers("/api/**").hasRole("ADMIN").
                anyRequest().authenticated().
                and().
                csrf().disable();

        http.exceptionHandling().
                authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 암호 저장시 해시 함수로 암호화
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/static/**", "/images/**", "/swagger-resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auto) throws Exception {
        auto.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }
}
