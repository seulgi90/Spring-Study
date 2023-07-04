package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;

public class ConfigurationSingtonTest {
	
	@Test
	void configurationTest() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		
		//테스트 용도인 getMemberRepository()꺼내기위해서 MemberServiceImpl.class로 진행 -> 구체타입으로 꺼내면 좋지않음에 유의!
		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class); 
		OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
		MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
		
		MemberRepository memberRepository1 = memberService.getMemberRepository();
		MemberRepository memberRepository2 = orderService.getMemberRepository();
		
		System.out.println("memberRepository1 = " +  memberRepository1);
		// memberRepository1 = hello.core.member.MemoryMemberRepository@2e8e8225
		System.out.println("memberRepository2 = " +  memberRepository2);
		// memberRepository2 = hello.core.member.MemoryMemberRepository@2e8e8225
		System.out.println("memberRepository = " +  memberRepository);
		// memberRepository = hello.core.member.MemoryMemberRepository@2e8e8225
		
		Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
		Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
		
		// ====> memberRepository 인스턴스는 모두 같은 인스턴스가 공유되어 사용된다
	}
	
	@Test
	void configurationDeep() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
		AppConfig bean = ac.getBean(AppConfig.class);
		
		System.out.println("bean == " + bean.getClass());
		// bean == class hello.core.AppConfig$$EnhancerBySpringCGLIB$$4e4d0505
		// 순수한 클래스라면 'class hello.core.AppConfig' 로 출력이되어야한다
		// xxxCGLIB가 붙으면 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 
		// AppConfig클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링빈으로 등록한 것
	}
	
	

}
