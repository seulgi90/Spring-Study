package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hello.hellospring.domain.Member;

class MemoryMemberRepositoryTest {
	
	MemoryMemberRepository repository = new MemoryMemberRepository();
	
	
	// 테스트케이스에 작성된 테스트들은 순서를 보장하지 않기때문에 테스트하나가 끝날때마다 repository 초기화
	@AfterEach
	public void afterEach() {
		repository.clearStore(); 
	}
	
	@Test
	public void save() {
		Member member = new Member();
		member.setName("spring");
		repository.save(member);
		Member result = repository.findById(member.getId()).get();
		
		//import org.junit.jupiter.api.Assertions;
//		Assertions.assertEquals(member, result);
		
		// import org.assertj.core.api.Assertions;
		Assertions.assertThat(member).isEqualTo(result); 
	}
	
	@Test
	public void findByName() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		Member result = repository.findByName("spring1").get();
		
		assertThat(result).isEqualTo(member1);
	}
	
	@Test
	public void findAll() {
		Member member1 = new Member();
		member1.setName("spring1");
		repository.save(member1);
		
		Member member2 = new Member();
		member2.setName("spring2");
		repository.save(member2);
		
		List<Member> result = repository.findAll();
		
		assertThat(result.size()).isEqualTo(2);
	}

}
