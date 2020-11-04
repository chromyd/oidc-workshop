package de.dbsystel.oidcworkshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurer() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                        .authorizeRequests()
                        .antMatchers("/").permitAll()
                        .antMatchers("/private/info").authenticated()
                        .anyRequest().denyAll()
                        .and()
                        .logout().logoutSuccessUrl("/")
                        .and()
                        .oauth2Login();
            }
        };
    }
}