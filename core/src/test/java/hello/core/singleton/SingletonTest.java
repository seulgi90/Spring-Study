package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;

public class SingletonTest {
	
	@Test
	@DisplayName("스프링 없는 순수한 DI 컨테이너")
	void pureContainer() {
		AppConfig appConfig = new AppConfig();
		
		// 1. 조회: 호출할 때 마다 객체를 생성
		MemberService memberService1 = appConfig.memberService();
		
		// 2. 조회: 호출할 때 마다 객체를 생성
		MemberService memberService2 = appConfig.memberService();
		
		// 참조값이 다른 것을 확인
		System.out.println("memberService1 = " + memberService1);
		// memberService1 = hello.core.member.MemberServiceImpl@22555ebf
		System.out.println("memberService2 = " + memberService2);
		// memberService2 = hello.core.member.MemberServiceImpl@36ebc363
		
		// memberService1 != memberService2 검증
		Assertions.assertThat(memberService1).isNotSameAs(memberService2);
		
		// 우리가 만들었던 스프링 없는 순수한  DI 컨테이너인 AppConfig는 요청을 할 떄 마다 객체를 새로 생성한다
		// => 해당 객체가 딱 1개만 생성되고 공유하도록 설계를 변경해야한다 (싱글톤 패턴)
	}
	
	@Test
	@DisplayName("싱글톤 패턴을 적용한 객체 사용")
	void singletonServiceTest() {
//		new SingletonService(); 
//  	The constructor SingletonService() is not visible : 컴파일 오류
//		SingletonService 클래스의 생성자가 private으로 선언되었거나 다른 가시성 제어자로 접근을 제한했기 때문에 외부에서 인스턴스를 생성할 수 없다는 뜻
		
		SingletonService singletonService1= SingletonService.getInstance();
		SingletonService singletonService2= SingletonService.getInstance();
		
		System.out.println("singletonService1 = " + singletonService1);
		// singletonService1 = hello.core.singleton.SingletonService@3c46e67a
		System.out.println("singletonService2 = " + singletonService2);
		// singletonService2 = hello.core.singleton.SingletonService@3c46e67a
		
		// isSameAs : '==' 
		// isEqualTo : 'equals'
		Assertions.assertThat(singletonService1).isSameAs(singletonService2);
	}
	
	@Test
	@DisplayName("스프링 컨테이너와 싱글톤")
	void springContainer() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		
		// 1. 조회: 호출할 때 마다 객체를 생성
		MemberService memberService1 = ac.getBean("memberService", MemberService.class);
		
		// 2. 조회: 호출할 때 마다 객체를 생성
		MemberService memberService2 = ac.getBean("memberService", MemberService.class);
		
		// 참조값이 다른 것을 확인
		System.out.println("memberService1 = " + memberService1);
		// memberService1 = hello.core.member.MemberServiceImpl@18920cc
		System.out.println("memberService2 = " + memberService2);
		// memberService1 = hello.core.member.MemberServiceImpl@18920cc
		
		// memberService1 == memberService2 검증
		Assertions.assertThat(memberService1).isSameAs(memberService2);
		
	}

}
