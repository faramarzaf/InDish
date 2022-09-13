package com.faraf.security;

import com.faraf.RoleType;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String user = RoleType.USER.getValue();
        String admin = RoleType.ADMIN.getValue();

        http
                .csrf().disable()

                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/user/login/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/user/register/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/v1/user/all/**").hasAuthority(admin)
                .regexMatchers(HttpMethod.GET, "/api/v1/user/email?\\w").hasAuthority(admin)

                .antMatchers(HttpMethod.DELETE, "/api/v1/user/deleteAll/**").hasAuthority(admin)
                .antMatchers(HttpMethod.DELETE, "/api/v1/user/deleteId/**").hasAuthority(admin)
                .antMatchers(HttpMethod.DELETE, "/api/v1/user/deleteEmail/**").hasAuthority(admin)

                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v3/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/", "/home").permitAll()

                .antMatchers("/users").hasAuthority(admin) // maybe better to role admin

                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").successForwardUrl("/home").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                .and()
                .logout().permitAll()


        //  .and()
        //  .httpBasic()
        ;
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
