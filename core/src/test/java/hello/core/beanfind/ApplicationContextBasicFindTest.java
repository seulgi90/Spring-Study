package hello.core.beanfind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class ApplicationContextBasicFindTest {
	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Test
	@DisplayName("빈 이름으로 조회")
	void findBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
//		System.out.println("memberService = " +  memberService);
//		System.out.println("memberService.getClass = " +  memberService.getClass());
		Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
	}
	
	// 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류 발생 -> 이때는 빈 이름을 지정하자
	@Test
	@DisplayName("이름 없이 타입으로만 조회")
	void findBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
	}

	// 구현체에 MemberServiceImpl 의존했기때문에 좋은 test코드는 아니다 
	@Test
	@DisplayName("구체 타입으로 조회")
	void findBeanByName2() {
		MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
		Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("빈 이름으로 조회X")
	void findBeanByNameX() {
//		MemberService memberService = ac.getBean("xxx", MemberService.class);
		
		org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class, 
				() -> ac.getBean("xxx", MemberService.class));
	}
}
