package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

// 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다
// 생성한 객체 인스턴스의 참조(래퍼런스)를 생성자를 통해서 주입(연결)
// 객체의 생성과 연결 담당
public class AppConfig_1 {

	// 이전에는 MemberServiceImpl가 private final MemberRepository memberRepository = new MemoryMemberRepository(); 와 같이
	// 직접  new MemoryMemberRepository()를 할당 했었다 
//	public MemberService memberService() {
//		return new MemberServiceImpl(new MemoryMemberRepository()); // 성성자 주입
//	}
	
	// 위 리팩토링
	public MemberService memberService() {
		return new MemberServiceImpl(memberRepository());
	}
	
	private MemberRepository memberRepository() {
		return new MemoryMemberRepository();
	}
	
//	public OrderService orderService() {
//		return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
//	}
	
	// 위 리팩토링
	public OrderService orderService() {
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	public DiscountPolicy discountPolicy() {
//		return new FixDiscountPolicy();
		return new RateDiscountPolicy();
	}
}
