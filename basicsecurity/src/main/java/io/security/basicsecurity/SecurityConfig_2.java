//package io.security.basicsecurity;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
////@EnableWebSecurity // 웹 보안 활성
//@Order(0) // 실행 순서 필수 설정
//public class SecurityConfig_2 extends WebSecurityConfigurerAdapter {
//    // 다중 설정 클래스
//    // 설정 클래스 별로 보안 기능이 각각 작동 : SecurityConfig1, SecurityConfig2
//    // 설정 클래스 별로 requestMatcher설정
//    // 설정 클래스 별로 필터가 생성
//    // FilterChainProxy - securityFilterChains 가 각 필터들을 가지고 있음
//    // 요청에 따라 requestMatcher와 매칭되는 필터가 작동하도록 함
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .antMatcher("/admin/**")
//            .authorizeRequests()
//            .anyRequest().authenticated();
//
//        http
//            .httpBasic();
//
//    }
//}
//
//@Configuration
//@Order(1) // 실행 순서 필수 설정
//class Security2 extends WebSecurityConfigurerAdapter {
//    protected void confiure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//            .anyRequest().permitAll()
//            .and()
//            .formLogin();
//    }
//}
//
//
