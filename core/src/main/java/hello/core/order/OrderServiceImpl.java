package hello.core.order;

import org.springframework.context.annotation.Configuration;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {
	
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
//	private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	// 할인 정책을 변경하려면 클라이언트인 'OrderServiceImpl'코드를 고쳐야한다 : new FixDiscountPolicy(); -> new RateDiscountPolicy();
	// OrderServiceImpl은 DiscountPolicy 인터페이스에 의존하면서도 DIP를 지킨것 같지만 아니다
	// OrderServiceImpl은 추상(인터페이스) DiscountPolicy 의존 할 뿐만 아니라
	// 구체(구현)클래스 'FixDiscountPolicy'와 'RateDiscountPolicy'에도 의존하고있다
//	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	
	// 추상(인터페이스)에만 의존하도록 변경 :DIP
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
		this.memberRepository = memberRepository;
		this.discountPolicy = discountPolicy;
	}

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice); // 등급만 넘겨도 되는건데 확장성을 고려하여 member로 넘김
		
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}

}
