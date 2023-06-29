package hello.core.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AppConfig;

public class ApplicationConfigContextInfoTest {

	
	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
	
	@Test
	@DisplayName("모든 빈 출력하기")
	void findAllBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		
		for (String beanDefinitionName : beanDefinitionNames) {
			Object bean = ac.getBean(beanDefinitionName);
			System.out.println("name = " +beanDefinitionName + " // Object = " + bean);
		}
	}
	
	@Test
	@DisplayName("애플리케이션 빈 출력하기")
	void findApplicationBean() {
		String[] beanDefinitionNames = ac.getBeanDefinitionNames();
		
		for (String beanDefinitionName : beanDefinitionNames) {
			BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
			
			//Role ROLE_APPLICATION: 직접 등록한 애플리케이션 빈
			//Role ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
			if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
				Object bean = ac.getBean(beanDefinitionName);
				System.out.println("name = " +beanDefinitionName + " // Object = " + bean);
				
//				name = appConfig // Object = hello.core.AppConfig$$Enhancer                                  BySpringCGLIB$$dd555579@72c28d64
//				name = memberService // Object = hello.core.member.MemberServiceImpl@6492fab5
//				name = memberRepository // Object = hello.core.MemoryMemberRepository@2c5529ab
//				name = orderService // Object = hello.core.order.OrderServiceImpl@39a8312f
//				name = discountPolicy // Object = hello.core.discount.RateDiscountPolicy@5f6722d3
			}
		}
	}
}
