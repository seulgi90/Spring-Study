package hello.core.member;

public class MemberServiceImpl implements MemberService {

	// DB가 아직 정해지지않았기 때문이나 
	// memberRepository 차상화에도 의존하고 MemoryMemberRepository 구현체에도 의존  -> DIP 위반
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	// 추상(인터페이스)에만 의존하도록 변경
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

}
