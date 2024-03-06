//package io.security.basicsecurity;
//
//import java.io.IOException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.logout.LogoutHandler;
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//
//@Configuration
////@EnableWebSecurity // 웹 보안 활성
//public class SecurityConfig_1 extends WebSecurityConfigurerAdapter {
//    // WebSecurityConfigurerAdapter : 스프링 시큐리티의 웹 보안 기능 초기화 및 설정
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    // AuthenticationManagerBuilder : 사용자 생성
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
//        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN");
//    }
//
//    // HttpSecurity : 세부적인 보안 기능을 설정 할 수 있는 API제공 - 인증 api(formLogin, csrf...), 인가 api(authorizeRequests, hasRole...)
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http
//            // 권한설정
//            // 선언적 방식 : url , Method
//            // 동적 방식 :  DB연동 url, Method
//
//            // 설정 시 구체적인 경로가 F먼저 오고 그것 보다 큰 범위의 경로가 뒤에 오도록 해야 한다
//            // /shop/login > ... > /shop/admin/pay > /shop/*admin/** (위에서 아래로 작동 유의)
//            // fullyAuthenticated() : 인증된 사용Ï자 접근 허용, rememberMe 인증 제외
//            // anonymous() :  익명 사용자 접근 허용, hasRole("USER") 등 접근 불가 -> 익명 사용자는 null아니고 익명권한을 가지고있는 객체가 존재!
//            // hasIpAddress(String) : 주어진 ip로부터 요청이 왔다면 접근 허용
////            .antMatcher("/shop/**") // 설정 한 경로만 -> 설정 없을 시 모든 페이지 인가 처리
////            .authorizeRequests()
////                .antMatchers("/shop/login", "/shop/users/**").permitAll() // 무조건 접근 허용 <-> denyAll()
////                .antMatchers("/shop/mypage").hasRole("USER") // 주어진 역할이 있다면 접근 허용
////                .antMatchers("/shop/admin/pay").access(("hasRole('ADMIN)")) // 주어진 SpEL표현식의 결과가 true면 접근 허용
////                .antMatchers("/Fshop/*admin/**").access("hasRole('ADMIN) or hasRole('SYS)")
//
//            .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/user").hasRole("USER")
//                .antMatchers("/admin/pay").hasRole("ADMIN")
//                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
//            .anyRequest().authenticated(); // 인증 된 사용자의 접근을 허용 하도록
//
//        http
//            // Csrf 사이트 간 요청 위조
//            // csrfFilter :  모든 요청에 랜덤하게 생성된 토큰을 http 파라미터로 요구
//            // -> 요청 시 전달되는 csrf토큰 값과 서버에 저장된 실제 값과 비교한 후 일치 하지 않으면 요청 실패
//            // 기본이 활성화
//            // 폼 방식 인 경우 클라이언트에서 Input hidden 등을 통해 csrf파라미터, csrfToken을 넘겨줘야함
//            // <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
//                .csrf();
////            .csrf().disable(); // 비활성화
//
//        http
//            .formLogin()
////          .loginPage("/loginPage") // 사용자 정의 로그인 페이지 -> 인가 없이 접속이 가능해야하는 화면
//            .defaultSuccessUrl("/") // 로그인 성공 후 이동 페이지
//            .failureForwardUrl("/login") // 로그인 실패 후 이동 페이지
//            .usernameParameter("userId") // 아이디 파라미터명 설정
//            .passwordParameter("passwd") // 패스워드 파라미터명 설정
////            .loginProcessingUrl("/login_proc") // 로그인 form action url
//            .successHandler(new AuthenticationSuccessHandler() {
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                    RequestCache requestCache = new HttpSessionRequestCache(); // 사용자의 요청 정보를 세션에 저장한다
//                    SavedRequest savedRequest = requestCache.getRequest(request, response); // 사용자가 요청했던 경로, 정보가 저장되어있다
//                    String redirect = savedRequest.getRedirectUrl();
//                    response.sendRedirect(redirect);
//
////                    System.out.println("authentication" + authentication.getName());
////                    response.sendRedirect("/");
//                }
//            });
////          .failureHandler(new AuthenticationFailureHandler() {
////            @Override
////            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
////              System.out.println("exception" + exception.getMessage());
////              response.sendRedirect("/login");
////            }
////          })
//
//
//
//        http
//            // logoutFilter -> 매치가 되면 -> authentication -> 로그인(인증)정보를 담고있는 securityContext에 저장 되어있는 인증객체를 꺼내와서
//            // -> securityContextLogoutHandler 에게 넘겨 주면 -> 세션무효화 -> 인증토큰 삭제 -> 쿠키정보 삭제 -> securityContextHolder.clearContext()로 securityContext 초기화 , 인증객체 초기화
//            // -> simpleUrlLogoutSuccessHandler 가 호출되면 로그인 페이지가 리다이렉트 된다
//            .logout()
//            .logoutUrl("/logout") // 스프링시큐리티가 제공하는 로그아웃은 post 방식이다
//            .logoutSuccessUrl("/login")
//            .addLogoutHandler(new LogoutHandler() {
//                @Override
//                public void logout(HttpServletRequest request, HttpServletResponse response,
//                    Authentication authentication) {
//                    HttpSession session = request.getSession();
//                    session.invalidate(); // 세션 무효화
//                }
//            })
//            .logoutSuccessHandler(new LogoutSuccessHandler() {
//                @Override
//                public void onLogoutSuccess(HttpServletRequest request,
//                    HttpServletResponse response,
//                    Authentication authentication) throws IOException, ServletException {
//                    response.sendRedirect("/login");
//                }
//            })
//            .deleteCookies("remember-me"); // 원하는 쿠키 삭제
//
//        http
//            // RememberMeAuthenticationFilter
//            // 세션이 만료되고 웹 브라우저가 종료된 후에도 어플리케이션이 사용자를 기억하는 기능
//            // remember-me 쿠키에 대한 http 요청을 확인한 후 토큰 기반 인증을 사용해 유효성을 검사하고
//            // 토큰이 검증되면 사용자는 로그인이 된다
//            // RememberMeAuthenticationFilter 작동: securityContext에 인증객체 없을 경우 or Remember-me 쿠키를 가져오는 경우
//            // -> rmemberMeServices : tokenBasedRememberMeServices(메모리에 저장된 쿠키 비교) / persistentTokenBasedRememberMeServices(db 저장 된 토큰 비교)
//            // -> token Cooke 추출 : 사용자가 가지고 있는 토큰이 remember-me에 담긴 토큰인지 검사
//            // -> token 존재 x : chain.doFilter
//            // -> token 존재 시 decode Token(정상 유무 판단) : 토큰 포맷이 정상인지 등 체크 -> 정상 아니면 Exception 발생
//            // -> token 일치 -> 해당 토큰의 User 계정 존재를 db에서 조회
//            // -> 존재 하면 새로운 authentication 생성 -> 인증 처리 authenticationManager
//            .rememberMe()
//            .rememberMeParameter("remember") // 기본 파라미터명은 remember-me
//            .tokenValiditySeconds(3600) // default 14일
////          .alwaysRemember(true) // remember-me 기능이 활성화 되지 않아도 항상 실행
//            .userDetailsService(userDetailsService); // remember-me 기능 사용 시 사용자 계정에 대한 조회를 수행하는 메서드(필수)
//
//        // AnonymousAuthenticationFilter : 익명사용자 인증 처리 필터
//        // 익명사용자와 인증 사용자를 구분해서 처리하기 위한 용도로 사용
//        // -> anonymousAuthenticationToken(익명사용자 인증객체) 생성하여 SecurityContext안에  anonymousUser Role_anonymous 로 객체 저장
//        // -> 익명사용자는  null로 저장되지 않는다
//        // 화면에서 인증 여부를 구현할 때 isAnonymous():익명 사용자 와 isAuthenticated(): 인증 사용자 로 구분
//        // 인증객체를 세션에 저장하지 않는다
//
//        http
//            // SessionManagementFilter
//            // 세션 관리 : 인증 시 사용자의 세션정보를 등록, 조회, 삭제 등 세션 이력 관리
//
//            // 동시 세션 제어 : 최대 세션 허용 개수 초과
//            // 1. 이전 사용자 세션 만료 설정(기존 세션 만료) :  session.expiredNow()
//            // 2. 현재 사용지 인증 실패로 돌리기(동시 로그인 차단)
////          .sessionManagement()
////          .maximumSessions(1) // 최대 허용 가능 세션 수, -1: 무제한 로그인 세션 허용
////          .maxSessionsPreventsLogin(true) // 동시 로그인 차단함, false : 기존 세션 만료 (default)
////          .invaildSessionUrl("/invalid") // 세션이 유효하지 않을 때 이동 할 페이지, invaildSessionUrl 과 expriredUrl 둘다 적용 했을 경우 invaildSessionUrl 우선 적용
////          .expriredUrl("/expired"); // 세션이 만료된 경우 이동 할 페이지
//
//            // 세션 고정 보호 : 공격자의 세션 가로채기, 조작 방지
//            // 인증 할 때 마다 세션쿠키를 새로 발급하여 공격자의 쿠키 조작을 방지
//            // changeSessionId(), none(), migrateSession(), newSession()
//            // migrateSession : 서블릿 3.1 이하 작동, 이전의 세션에 설정한 값을 그대로 사용
////            .sessionManagement()
////            .sessionFixation().changeSessionId() // 기본값 : 사용자가 인증을 성공하게 되면 사용자의 세션은 그대로 두고, 세션ID만 변경되도록 하는 정책
//
//            // 세션 생성 정책
//            // SessionCreationPolicy.Always : 스프링시큐리티가 항상 세션 생성
//            // SessionCreationPolicy.IF_REQUIRED : 스프링시큐리티가 필요 시 세션 생성(기본 값)
//            // SessionCreationPolicy.Never : 스프링시큐리티가 세션을 생성하지 않지만 이미 존재하면 사용
//            // SessionCreationPolicy.Stateless : 스프링시큐리티가 세션을 생성하지 않고 존재해도 사용하지 않음 -> JWT 토큰 방식 일 경우,
////            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//
//            // ConcurrentSessionFilter
//            // 매 요청마다 현재 사용자의 세션 만료 여부 체크
//            // 세션이 만료되었을 경우 즉시 만료 처리
//            // session.isExpired() == true 면 로그아웃 처리, 즉시 오류 페이지 응답
//            // user1 로그인 -> ConcurruntSEssionControlAuthticationStrategy > onAuthentication >  sessionCount = 0
//            // -> ChangeSessionIdAuthenticationStrategy > session.changeSessionId() 로 새로운 세션 생성
//            // -> RegisterSessionAuthenticationStrategy > session1 로 세션 정보 등록 > 인증 성공
//            // user2 로그인 -> ConcurruntSEssionControlAuthticationStrategy > onAuthentication >  sessionCount == macSessions
//            // -> 인증 실패 전략 인 경우 : 인증 실패 처리
//            // -> 세션 만료 전략 인 경우: session.expireNow(): user1
//            // -> ChangeSessionIdAuthenticationStrategy > session.changeSessionId() 로 새로운 세션 생성
//            // -> RegisterSessionAuthenticationStrategy > session2 로 세션 정보 등록
//            // -> ConcurrentSessionFilter > user1의 session.isExpired() > 로그아웃 처리 또는 오류 페이지
//            .sessionManagement()
//            .maximumSessions(1)
//            .maxSessionsPreventsLogin(true); // 세션 허용 개수 초과 시 인증실패 전략(동시 로그인 차단)
//
//        http
//            // 예외처리 기능
//            // FilterSecurityInterceptor
//            // ExceptionTranslationFilter exceptionTranslationFilter;
//            .exceptionHandling()
//
//            // AuthenticationException : 인증 예외 처리
//            // -> authenticationEntryPoint 호출 : 로그인 페이지 이동, 401 오류 코드 전달 등
//            // -> 인증 예외가 발생하기 전의 사용자의 요청 정보를 저장 : RequestCache, SavedRequest
//            // -> 인증 실패 이후 처리 authenticationEntryPoint : securityContext null로 초기화
//            // -> 로그인 페이지 이동
////            .authenticationEntryPoint(new AuthenticationEntryPoint() {
////                @Override
////                public void commence(HttpServletRequest request, HttpServletResponse response,
////                    AuthenticationException authException) throws IOException, ServletException {
////                    response.sendRedirect("/login"); // 인증 실패 시 로그인 페이지 이동
////                }
////            })
//
//            // -> AccessDeninedException : 인가 예외 처리
//            // -> accessDeniedHandler 에서 예외 처리하도록 제공
//            // -> 자원에 접근할 수 있는 권한이 없는 denied 페이지 이동
//            .accessDeniedHandler(new AccessDeniedHandler() {
//                @Override
//                public void handle(HttpServletRequest request, HttpServletResponse response,
//                    AccessDeniedException accessDeniedException)
//                    throws IOException, ServletException {
//                    response.sendRedirect("/denied");
//                }
//            });
//
//        // springBean -> servlet Filter : 서블릿 필터는 스프링에서 정의된 빈을 주입해서 사용 할 수 없음
//        // servlet Filter이 가장 먼저 요청 받게됨
//        // -> DelegationFilterProxy : springWecurityFilterChain 이름으로 생성된 빈을 ApplicationContext에서 찾아 요청 위임
//        // -> (요청 위임) Spring Bean -> servlet Filter
//
//        // spring container - FilterChainProxy
//        // springWecurityFilterChain : servlet container - DelegationFilterProxy으로 부터 요청을 위임 받고 실제 보안 처리
//        // 스프링 시큐리티 초기화 시 생성되는 필터들을 관리하고 제어
//        // 사용자의 요청을 필터 순서대로 호출하여 전달
//        // 사용자정의 필터를 생성해서 기존의 필터 전.후로 추가 가능 : 필터의 순서 중요
//        // SecurityFilterAutoConfiguration
//
//    }
//}