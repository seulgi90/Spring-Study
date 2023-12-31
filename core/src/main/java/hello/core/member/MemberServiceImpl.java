package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

	// DB가 아직 정해지지않았기 때문이나 
	// memberRepository 차상화에도 의존하고 MemoryMemberRepository 구현체에도 의존  -> DIP 위반
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	// 추상(인터페이스)에만 의존하도록 변경
	// 자동 의존관계를 주입 , @Component 쓰게되면 빈이 자동으로 등록이 되긴하나, 의존관계를 설정 할수 있는 방법이 없기때문에 @Autowired를 사용하여 의존관계를 주입
	@Autowired  // 자동으로 ac.getBean(MemberRepository.class) 코드가 들어간다고 생각하면된다
	private final MemberRepository memberRepository;
	
	public MemberServiceImpl(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public void join(Member member) {
		memberRepository.save(member);
	}

	@Override
	public Member findByMember(Long memberId) {
		return memberRepository.findById(memberId);
	}
	
	// 테스트 용도
	public MemberRepository getMemberRepository() {
		return memberRepository;
	}

}
