package com.example.cms.config;


import com.example.cms.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
                // loadUserByUsername() 구현해야함 (서비스 참고)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    }
    @Override protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/javainuse-openapi/**").permitAll()
                .antMatchers("/login").permitAll() // 누구나 접근 허용
                .antMatchers("/api/v1/signup").permitAll()
                .antMatchers("/api/v1/**").hasAnyRole("MEMBER", "ADMIN") // MEMBER, ADMIN만 접근 가능
                .antMatchers("/api/v1/test/**").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated();

        http
                .formLogin()
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutUrl("/logout");
    }
}

