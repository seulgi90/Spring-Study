package hello.hellospring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;

// 자바 코드로 직접 스프링 빈 등록하기
@Configuration
public class SpringConfig {
	
//	private DataSource dataSource;
//	
//	@Autowired
//	public SpringConfig(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
	
//	private EntityManager em;
//	
//	@Autowired
//	public SpringConfig(EntityManager em) {
//		this.em = em;
//	}
	
	private final MemberRepository memberRepository;
	
	@Autowired
	public SpringConfig(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Bean
	public MemberService memberService() {
//		return new MemberService(memberRepository());
		return new MemberService(memberRepository);
	}
	// 빈 등록 대신 TimeTraceAop 위에 @Component 표시로 대체
//	@Bean
//	public TimeTraceAop tionTraceAop() {
//		return new TimeTraceAop();
//	}
	
//	@Bean
//	public MemberRepository memberRepository() {
//		return new MemoryMemberRepository();
//		return new JdbcMemberRepository(dataSource);
//		return new JdbcTemplateMemberRepository(dataSource);
//		return new JpaMemberRepository(em);
//	}
}
