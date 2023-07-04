package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {
	
	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);
		
		// ThreadA: A사용자 10000원 주문
		statefulService1.order("userA", 10000);
		
		// ThreadB: B사용자 20000원 주문
		statefulService2.order("userB", 20000);
		
		// ThreadA: 사용자A 주문 금액 조회
		// 기대값: 10000, 결과값: 20000
		int price = statefulService1.getPrice(); 
		System.out.println(" price = " +  price); // 20000
		Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
		
		
	}
	
	@Test
	void statelessServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatelessService statelessService1 = ac.getBean(StatelessService.class);
		StatelessService statelessService2 = ac.getBean(StatelessService.class);
		
		// ThreadA: A사용자 10000원 주문
		int userAPrice = statelessService1.order("userA", 10000); // 지역변수를 사용하여 문제를 해결 
		
		// ThreadB: B사용자 20000원 주문
		int userBPrice = statelessService2.order("userB", 20000);
		
		// ThreadA: 사용자A 주문 금액 조회
		// 기대값: 10000, 결과값: 10000
		System.out.println(" price = " +  userAPrice); 
		
		
	}
	
	static class TestConfig {
		
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
		
		@Bean
		public StatelessService statelessService() {
			return new StatelessService();
		}
	}
}
