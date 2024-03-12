package security.coreSpringsecurity.security.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CharacterEncodingFilter;
import security.coreSpringsecurity.security.provider.CustomAuthenticationProvider;
import security.coreSpringsecurity.service.UserService;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    // 사용자 지정 로직이나 외부 시스템과의 통합이 필요한 경우에 이 방법을 사용
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        http
                .authorizeRequests()
                .antMatchers("/","/users","user/login/**").permitAll()
                .antMatchers("/mypage").hasRole("USER")
                .antMatchers("/messages").hasRole("MANAGER")
                .antMatchers("/config").hasRole("ADMIN")
                .anyRequest().authenticated()
        .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // createDelegatingPasswordEncoder :  여러개의 PasswordEncoder 유형을 선언 한 뒤, 상황에 맞게 선택해서 사용할 수 있도록 지원
        // 암호화 포맷 : {암호화방식} encodedPassword , 기본 포맷은 {bcrypt}

//        암호 포맷 변경 예제
//        String encodingId = "MD5";
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//=====================================================================================================================
// 메모리 방식으로 직접 사용자를 설정, 권한 설정 후 인증 처리
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        String password = passwordEncoder().encode("1111");
//        auth.inMemoryAuthentication().withUser("user").password(password).roles("USER");
//        auth.inMemoryAuthentication().withUser("manager").password(password).roles("MANAGER","USER");
//        auth.inMemoryAuthentication().withUser("admin").password(password).roles("ADMIN","MANAGER","USER");
//    }

//=====================================================================================================================
//  userDetailsService 로 만든 구현체로 인증 처리를 하게 된다
// 실제 사용자 정보가 데이터베이스나 외부 시스템에 저장되어 있는 경우에 사용

//    @Autowired
//    private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
}
