package hello.core.autowired;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;

public class AllBeanTest {

	@Test
	void findAllBean() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
		
		DiscountService discountService = ac.getBean(DiscountService.class);
		Member member = new Member(1L, "userA", Grade.VIP);
		int fixDiscountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
		
		Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
		Assertions.assertThat(fixDiscountPrice).isEqualTo(1000);
		
		int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
		Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);
	}
	
	static class DiscountService {
		private final Map<String, DiscountPolicy> policyMap;
		private final List<DiscountPolicy> policies;
		
		// 생성자 하나라서 @Autowired 생략 가능
		public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
			this.policyMap = policyMap;
			this.policies = policies;
			
			System.out.println("policyMap" +  policyMap); // DiscountService.class 만 빈으로 등록 했을 경우 :  policyMap{}
			// policyMap{fixDiscountPolicy=hello.core.discount.FixDiscountPolicy@4233e892, rateDiscountPolicy=hello.core.discount.RateDiscountPolicy@77d2e85}
			System.out.println("policies" +  policies); // DiscountService.class 만 빈으로 등록 했을 경우 :  policies[]
			// policies[hello.core.discount.FixDiscountPolicy@4233e892, hello.core.discount.RateDiscountPolicy@77d2e85]
			
		}

		public int discount(Member member, int price, String discountCode) {
			DiscountPolicy discountPolicy = policyMap.get(discountCode);
			return discountPolicy.discount(member, price);
		}
	}
}
