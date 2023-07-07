package hello.core.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // final이 붙은 필드를 파라미터로 받는 기본생성자를 만들어준다
public class OrderServiceImpl implements OrderService {
	
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
//	private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
	// 할인 정책을 변경하려면 클라이언트인 'OrderServiceImpl'코드를 고쳐야한다 : new FixDiscountPolicy(); -> new RateDiscountPolicy();
	// OrderServiceImpl은 DiscountPolicy 인터페이스에 의존하면서도 DIP를 지킨것 같지만 아니다
	// OrderServiceImpl은 추상(인터페이스) DiscountPolicy 의존 할 뿐만 아니라
	// 구체(구현)클래스 'FixDiscountPolicy'와 'RateDiscountPolicy'에도 의존하고있다
//	private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
	
	// 추상(인터페이스)에만 의존하도록 변경 :DIP
	// 오직 생성자 주입 방식만 final을 사용 할 수 있다 : 수정자(setter) 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출 되기때문
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

//	-------> @RequiredArgsConstructor 적용하면 기본 생성자가 자동으로 생성되기때문에 주석처리
//	@Autowired
//	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//		this.memberRepository = memberRepository;
//		this.discountPolicy = discountPolicy;
//	}

	@Override
	public Order createOrder(Long memberId, String itemName, int itemPrice) {
		Member member = memberRepository.findById(memberId);
		int discountPrice = discountPolicy.discount(member, itemPrice); // 등급만 넘겨도 되는건데 확장성을 고려하여 member로 넘김
		
		return new Order(memberId, itemName, itemPrice, discountPrice);
	}
	
	// 테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}

}
