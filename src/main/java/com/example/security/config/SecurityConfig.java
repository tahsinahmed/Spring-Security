package com.example.security.config;

import com.example.security.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.security.enums.Permission.STUDENT_READ;
import static com.example.security.enums.Permission.STUDENT_WRITE;
import static com.example.security.enums.Role.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeHttpRequests((auth) ->
                        auth
                                .antMatchers("/").permitAll()
//                                .antMatchers("/v1/**").hasRole(STUDENT.name())
//                                .antMatchers(HttpMethod.GET, "/v1/**").hasAuthority(STUDENT_READ.getPermission())
//                                .antMatchers(HttpMethod.POST, "/v1/**").hasAuthority(STUDENT_WRITE.getPermission())
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("tahsin")
                        .password(passwordEncoder.encode("password"))
//                        .roles(STUDENT.name())
                        .authorities(STUDENT.getGrantedAuthority())
                        .build(),
                User.builder()
                        .username("amit")
                        .password(passwordEncoder.encode("amit"))
//                        .roles(ADMIN.name())
                        .authorities(ADMIN.getGrantedAuthority())
                        .build(),

                User.builder()
                        .username("Walid")
                        .password(passwordEncoder.encode("walid"))
//                        .roles(ADMIN_TRAINEE.name())
                        .authorities(ADMIN_TRAINEE.getGrantedAuthority())
                        .build()
        );
    }
}
