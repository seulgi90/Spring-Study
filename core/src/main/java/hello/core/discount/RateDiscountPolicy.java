package hello.core.discount;

import org.springframework.stereotype.Component;

import hello.core.annotation.MainDiscountPoliscy;
import hello.core.member.Grade;
import hello.core.member.Member;

@Component
//@Qualifier("mainDiscountPolicy") // 추가 구분자, @Qualifier 끼리 매칭
//@Primary // 우선순위를 정한다, @Autowired 시에 여러 빈이 매칭되면 @Primary가 우선권을 가진다
// @Primary는 기본값처럼 동작, @Qualifier는 매우 상세하게 동작 -> Qualifier는가 우선권이 높다
@MainDiscountPoliscy // 내가 만든 어노테이션 적용
public class RateDiscountPolicy implements DiscountPolicy {
	
	private int discountPercent = 10;

	@Override
	public int discount(Member member, int price) {
		if (member.getGrade() == Grade.VIP) {
			return price * discountPercent / 100 ;
		} else {
			
			return 0;
		}
	}

}
