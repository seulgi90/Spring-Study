package hello.hellospring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

//@Service
@Transactional
public class MemberService {
	
	
	// MemberService에서 만든 new MemoryMemberRepository()와 
	// MemberServiceTest에서 만든 memberRepository() 는 다른 인스턴스이다 -> 같은 인스턴스를 사용하도록 변경해야 한다
//	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	private final MemberRepository memberRepository;
	
	// new로 직접 생성하는 것이 아닌 외부에서 주입 하도록 변경 (DI)
//	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public Long join(Member member) { 
		
		// 방법 1
//		Optional<Member> result = memberRepository.findByName(member.getName());
//		result.ifPresent(m -> {
//			throw new IllegalArgumentException("이미 존재하는 회원입니다");
//		});
		
		// 방법 1을 더 간편하게 
//		memberRepository.findByName(member.getName())
//			.ifPresent(m -> {
//				throw new IllegalArgumentException("이미 존재하는 회원입니다");
//			});
		// aop 적용 후 제거 
//		long start = System.currentTimeMillis();
//		
//		try {
			
			// 방법 1을 메서드로 따로 정의 
			validateDuplicateMember(member); // 증복 회원 검증
			
			memberRepository.save(member);
			return member.getId();
//		} finally {
//			long finish = System.currentTimeMillis();
//			long timeMs = finish - start;
//			System.out.println("join= " + timeMs + "ms");
//		}
//		
		 
	}
	
	/** 
	 * 전체 회원 조회
	 */
	private void validateDuplicateMember(Member member) {
		
		memberRepository.findByName(member.getName())
		.ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다");
		});
	}
	
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
