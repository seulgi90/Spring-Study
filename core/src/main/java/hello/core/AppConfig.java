package hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정(구성)정보로 사용
// @Configuration을 생략하고 @Bean만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다
@Configuration
public class AppConfig {
	
	// call 예상값 ( 메서드 호출은 순서를 보장하지않음에 유의!) : 5번 호출이되는가?
	// Call AppConfig.memberService
	// Call AppConfig.memberRepository
	// Call AppConfig.memberRepository
	// Call AppConfig.orderService
	// Call AppConfig.memberService
	
	// call 실제값 : 3번 호출
	// Call AppConfig.memberService
	// Call AppConfig.memberRepository
	// Call AppConfig.orderService
	
	// 여기서 @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록 -> 이렇게 스프링 컨테이너에 등록된 객체를 스프링 빈이라 함
	@Bean
	public MemberService memberService() {
		System.out.println("Call AppConfig.memberService");
		return new MemberServiceImpl(memberRepository());
	}
	@Bean
	public MemberRepository memberRepository() {
		System.out.println("Call AppConfig.memberRepository");
		return new MemoryMemberRepository();
	}
	
	@Bean
	public OrderService orderService() {
		System.out.println("Call AppConfig.orderService");
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	@Bean
	public DiscountPolicy discountPolicy() {
//		return new FixDiscountPolicy();
		return new RateDiscountPolicy();
	}
}
