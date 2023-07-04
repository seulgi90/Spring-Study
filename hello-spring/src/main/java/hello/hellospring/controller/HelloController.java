package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	// 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버가 화면을 찾아서 처리
	// 스프링부트 템플릿엔진 기본 viewName 매핑
	// resources:templates/ + {viewName}+ .html 
	@GetMapping("hello")
	public String hello(Model model) {
		model.addAttribute("data", "hello!!");
		return "hello";
	}
	
	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		return "hello-templates";
	}
	
	@GetMapping("hello-string")
	@ResponseBody
	public String helloString(@RequestParam("name") String name) {
		
		return "hello " + name;
	}
	
	// json 방식 http://localhost:8080/hello-api?name=spring123
	// {"name":"spring123"}
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(@RequestParam("name") String name) {
		// 객체가 오면 json 방식으로 데이터를 만들어서 반환하는것이 기본으로되어있다
		Hello hello = new Hello();
		hello.setName(name);
		return hello;
		
	}
	
	static class Hello {
		private String name;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	
	 

}
