package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }//단방향 암호화
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_C > ROLE_B\n" +
                "ROLE_B > ROLE_A");

        return hierarchy;
    }   //권한 우위 설정

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                );//접근 권한 설정 (ROLEHierrarchy)로 설정변경가능

//        http
//                .csrf(AbstractHttpConfigurer::disable
//                );//위조 방지 토큰 기능 off

        http
                .formLogin((auth)-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );//폼로그인 방식, 로그인 페이지 오류 방지, 로그인 처리 보안
//        http
//                .httpBasic(Customizer.withDefaults()) //basic 방식 로그인

        http
                .sessionManagement((auth) -> auth
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true));   //아이디 동시접속수, 초과하면 새로운 로그인 차단
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().newSession()
                );
        http
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/"));


        return http.build();
    }

//    @Configuration
//    @EnableWebSecurity
//    public class SecurityConfig {
//
//        @Bean
//        public UserDetailsService userDetailsService() {
//
//            UserDetails user1 = User.builder()
//                    .username("user1")
//                    .password(bCryptPasswordEncoder().encode("1234"))
//                    .roles("ADMIN")
//                    .build();
//
//            UserDetails user2 = User.builder()
//                    .username("user2")
//                    .password(bCryptPasswordEncoder().encode("1234"))
//                    .roles("USER")
//                    .build();
//
//            return new InMemoryUserDetailsManager(user1, user2);
//        }
//    }   //in memory 방식으로 유저 저장
    
}
