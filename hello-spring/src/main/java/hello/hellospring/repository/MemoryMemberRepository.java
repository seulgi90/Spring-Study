package hello.hellospring.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import hello.hellospring.domain.Member;

//@Repository
public class MemoryMemberRepository implements MemberRepository {
	
	private static Map<Long, Member> store = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Member save(Member member) {
		 member.setId(++sequence);
		 store.put(member.getId(), member);
		 return member;
	}

	@Override
	public Optional<Member> findById(Long id) {
		return Optional.ofNullable(store.get(id)); 
		// store.get(id) = null 이여도 Optional로 감싸서 보냄 -> 클라이언트 단에서 무언가를 할수 있따
	}

	@Override
	public Optional<Member> findByName(String name) {
		return store.values().stream() // 자바 람다식
				.filter(member -> member.getName().equals(name))
				.findAny(); // 하나라도 찾으면 반환 
	}

	@Override
	public List<Member> findAll() {
		return new ArrayList<Member>(store.values());
	}
	
	public void clearStore() {
		store.clear();
	}
	

}
