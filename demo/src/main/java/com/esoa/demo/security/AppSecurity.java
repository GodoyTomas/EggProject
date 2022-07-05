package com.esoa.demo.security;



import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.esoa.demo.service.UserService;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class AppSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder user) throws Exception {
        user.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .authorizeRequests()
                    .antMatchers("/auth/signup","/", "/auth/register", "/static/*").permitAll()
                    .antMatchers("/*").authenticated()
                .and()
                .formLogin()
                    .loginPage("/auth/login")
                        .loginProcessingUrl("/logincheck")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .permitAll()
                .and()
                    .csrf()
                    .disable();
        // @formatter:on
    }
    
}
