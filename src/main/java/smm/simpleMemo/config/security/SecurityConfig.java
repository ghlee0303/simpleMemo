package smm.simpleMemo.config.security;

import smm.simpleMemo.config.handler.AuthDeniedHandler;
import smm.simpleMemo.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    private UserDetailService userDetailService;
    private AuthenticationConfiguration authenticationConfiguration;
    private AccessDeniedHandler loginDeniedHandler;
    private AuthenticationEntryPoint entryPoint;

    public SecurityConfig(UserDetailService userDetailService,
                          AuthenticationConfiguration authenticationConfiguration,
                          AuthDeniedHandler loginDeniedHandler,
                          AuthenticationEntryPoint entryPoint) {
        this.userDetailService = userDetailService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.loginDeniedHandler = loginDeniedHandler;
        this.entryPoint = entryPoint;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService);

        http
                .authorizeRequests()
                    .antMatchers("/login").permitAll()
                    .antMatchers("/join").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(jsonLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                    .csrf().disable()
                    .formLogin().disable()
                    .logout()
                .and()
                    .exceptionHandling().accessDeniedHandler(loginDeniedHandler)
                    .authenticationEntryPoint(entryPoint);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/script/**")
                .antMatchers("/css/**");
    }

    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }


    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public JsonLoginFilter jsonLoginFilter(AuthenticationManager authenticationManager) {
        JsonLoginFilter jsonLoginFilter = new JsonLoginFilter();
        jsonLoginFilter.setAuthenticationManager(authenticationManager);
        jsonLoginFilter.setAuthenticationFailureHandler(loginFailHandler());

        return jsonLoginFilter;
    }

}

/*
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/loginProc")
                    .usernameParameter("email")
                    .passwordParameter("pw")
                    .defaultSuccessUrl("/", true)
                    .permitAll()*/