package com.faraf;

import com.faraf.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String user = RoleType.USER.getValue();
        String admin = RoleType.ADMIN.getValue();

        http
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/api/v1/user/register").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/food/save").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.POST, "/api/v1/ingredient/save").hasAnyRole(user, admin)


                .antMatchers(HttpMethod.GET, "/api/v1/user/all/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/user/by-id/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/user/username/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/user/email/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/by-id/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/by-username/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/by-country/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/vegans/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/non-vegans/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/by-time-required/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/food/by-time-required-between/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/ingredient/by-food-name/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.GET, "/api/v1/ingredient/by-food-id/**").hasAnyRole(user, admin)

                .antMatchers(HttpMethod.PUT, "/api/v1/user/update/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.PUT, "/api/v1/food/update/**").hasAnyRole(user, admin)

                .antMatchers(HttpMethod.DELETE, "/api/v1/user/deleteAll/**").hasRole(admin)
                .antMatchers(HttpMethod.DELETE, "/api/v1/user/deleteId/**").hasAnyRole(user, admin)
                .antMatchers(HttpMethod.DELETE, "/api/v1/user/deleteEmail/**").hasAnyRole(user, admin)

                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
