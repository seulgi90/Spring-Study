package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    // Authentication
    // 인증 시 Id 와 password를 담고 인증 검증을 위해 Authentication 객체를 생성
    // -> 인증 후 AuthenticationManager가 최종 인증 결과(user객체, 권한 정보)를 Authentication 객체에 담고
    // -> securityContext에 저장되어 전역적으로 참조 가능
    // -> ThreadLocal - Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
    // Principal : 사용자 아이디 혹은 user객체를 저장
    // credentials : 사용자 비밀번호
    // authorities : 인증 된 사용자의 권한 목록
    // details : 인증 부가 정보
    // authenticated : 인증 여부
    
    // securityContext
    // Authentication 객체가 저장되는 클래스
    // ThreadLocal(Thread들끼리 공유 되는 공간이 아님)에 저장되어 참조가 가능하도록 설계
    // -> 인증 완료 시 httpSession에 저장되어 전역적인 참조 가능
    
    // SecurityContextHolder
    // securityContext 객체 저장 방식
    // MODE_THREADLOCAL : 스레드 당 securityContext 객체를 할당, 기본값
    // MODE_INHERITABLETHREADLOCAL :  메인 스레드와 자식 스레드에 관하여 동일한 SecurityContext 를 유지
    // MODE_GLOBAL : 응용 프로그램에서 단 하나의 SecurityContext를 저장
    
    // 로그인 요청 -> 요청을 받은 서버가 스레드 가 생성되고 ThreadLocal가 스레드 마다 할당 됨
    // -> 인증 요청 정보를  Authentication 객체에 저장하고 인증
    // -> 인증 성공 시 성공 결과를 SecurityContextHolder > securityContext > Authentication 객체에 저장
    // -> securityContext가 'SPRING_SECURITY_CONTEXT'이름으로 httpSession에 저장
    
   protected void confiugure(HttpSecurity http) throws Exception {
       http
           .authorizeRequests()
           .anyRequest().authenticated();
       http
           .formLogin();
       // SecurityContextHolder 객체 저장 방식 설정
       SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
   }

}
