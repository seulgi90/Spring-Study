package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp_1 {

	public static void main(String[] args) {
		
//		MemberService memberService = new MemberServiceImpl();
		
		// 위처럼 직접 설정하지않고 AppConfig 활용
		AppConfig appConfig = new AppConfig();
		MemberService memberService = appConfig.memberService();
		
		Member member = new Member(1L, "memberA", Grade.VIP);
		memberService.join(member);
		
		Member findMember = memberService.findByMember(1L);
		System.out.println("new member = " + member.getName());
		System.out.println("find Member = " + findMember.getName());
	}
}
