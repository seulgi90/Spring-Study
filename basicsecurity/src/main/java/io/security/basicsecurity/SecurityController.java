package io.security.basicsecurity;

import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {
  
    @GetMapping("/")
    public String index() {
      return "home";
    }
    
    @GetMapping("/")
    public String index(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext context = (SecurityContext) session.getAttribute((HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY));
        Authentication authentication1 = context.getAuthentication();
        // authentication 와 authentication1 는 동일한 객체를 참조하고있다~~
        return "home";
    }
    
    @GetMapping("/thread")
    public String thread() {
        // MODE_THREADLOCAL  : 메인 스레드와 자식 스레드는 SecurityContext 객체를 공유 할수 없다 ( 기본값)
        // MODE_INHERITABLETHREADLOCAL : 메인 스레드와 자식 스레드에 관하여 동일한 SecurityContext 를 유지
        // MODE_GLOBAL : 응용 프로그램에서 단 하나의 SecurityContext를 저장
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                }
            }
        ).start();
        return "thread";
    }
    
//    @GetMapping("loginPage")
//    public String loginPage() {
//      return "loginPage";
//    }
    
    @GetMapping("/user")
    public String user() {
        return "user";
    }
    
    @GetMapping("/admin/pay")
    public String adminPay() {
        return "adminPay";
    }
    
    @GetMapping("/admin/**")
    public String admin() {
        return "admin";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/denied")
    public String denied() {
        return "Access is denied";
    }
    
}
