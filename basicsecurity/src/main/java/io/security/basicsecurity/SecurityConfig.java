package io.security.basicsecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    
    // c
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
           .antMatchers("/user").hasRole("USER")
           .anyRequest().permitAll();
       http
           .formLogin();
       // SecurityContextHolder 객체 저장 방식 설정
       SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    
       // SecurityContextPersistenceFilter - HttpSecurityContextRepository : securityContext 객체의 생성, 저장, 조회
       // 사용자의 요청을 SecurityContextPersistenceFilter 이 받아
       // -> HttpSecurityContextRepository 에서 인증 전/후 판단하여
       // -> 인증 전 : 새로운 컨텍스트 생성 SecurityContextHolder > securityContext = null
       //             -> authFilter -> 인증 후 SecurityContextHolder > securityContext > Authentication 인증 객체 저장
       //             -> SecurityContextPersistenceFilter가 Session에 SecurityContext를 저장 -> 응답
       // -> 인증 완료 : session에 저장되어있는 SecurityContext를 찾는다 -> SecurityContextHolder > securityContext > Authentication 객체에 저장
    
       // Authentication Flow
       // 로그인 요청 ->  usernamePasswordAuthenticationFilter
       // -> Authentication id + pass 담은 인증 전 토큰 객체 생성 -> authenticate(Authentication)
       // -> AuthenticationManager : 인증의 전반전인 관리, 실제 인증 역할 하지는 않고 AuthenticationProvider에 위임
       // -> AuthenticationProvider : loadUserByUsername(username) 실제 인증 처리 역할, 유저 유효성 검증(패스워드 체크 등)
       // -> UserDetailsService : findById() 유저 객체 조회, userDetails 타입으로 변환
       // -> Repository -> UserDetailsService : User 반환
       // -> AuthenticationProvider : UserDetails 반환
       // -> AuthenticationManager : Authentication(UserDtails + authorities) 담은 인증 후 토큰 객체 생성
       // -> usernamePasswordAuthenticationFilter : Authentication 반환
       // -> SecurityContext에 인증 객체 저장
    
       // AuthenticationManager -> AuthenticationProvider 로 인증 위임
       // -> authenticate(authentication: 사용자가 입력한 id/password) : id 검증, password 검증, 추가 검증
       // -> id 검증  -> UserDetailsService -> 인증 시 UserDetails 반환
       // -> password 검증
       // -> 추가 검증
       // authentication(user, authorities) -> AuthenticationManager로 반환
    
       // Authorization 인가 (권한)
       // FilterSecurityInterceptor : 인증 여부 체크 , 인증객체 없으면 exception
       // -> securityMetadataSource :  사용자가 요청한 자원에 필요한 권한 정보 조회해서 전달, 권한정보 있나?(null일 경우 자원 접근 허용)
       // -> AccessDecisionManager :  최종 심의 결정자, 접근 승인 ok? ->  자원 접근 허용
       
       // AccessDecisionManager : 인증 정보, 요청 정보, 권한 정보를 이용해서 사용자의 자원접근을 허용할 것인지 최종 결정
       // affirmativeBased : 여러개의 voter클래스 중 하나라도 접근 허가로 결론을 내면 접근 허가로 판단
       // consensusBased : 다수표에 의해 최종 결정을 판단, 동수일 경우 기본은 접근허가이나 설정으로 접근거부로 변경 가능
       // unanimousBased : 모든 voter가 만장일치로 접근을 승인해야 접근 가능, 그 외 접근 거부
   }

}
