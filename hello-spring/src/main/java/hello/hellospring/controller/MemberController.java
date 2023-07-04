package hello.hellospring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;

// 스프링컨테이너가 @Controller있으면 MemberController 객체를 생성한 후 컨테이너안에 넣어두고 관리를 한다 = 스프링 빈이 관리된다
@Controller
public class MemberController {

	private final MemberService memberService;

	// 컨트롤러와 서비스를 연결 
	// MemberController 생성 될 때 스프링에 등록되어있는 MemberService 객체를 가져다가 자동으로 주입 (DI)
	// 	@Autowired를 통한 DI는 MemberController, MemberService 등과 같이 스프링이 관리하는 객체에서만 동작한다
	// 스프링 빈으로 등록하지 않고(에: @Service 가없는) 내가 직접 생성한 객체에서는 동작하지 않는다
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping("/members/new")
	public String createForm() {
		return "members/createMemberForm";
	}
	
	@PostMapping("/members/new")
	public String create(MemberForm form) {
		Member member = new Member();
		member.setName(form.getName());
		
		memberService.join(member);
		
		return "redirect:/";
	}
	
	@GetMapping("/members")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		
		model.addAttribute("members", members);
		
		return "members/memberList";
	}
	
	
}
