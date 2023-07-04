package hello.hellospring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hello.hellospring.domain.Member;

// 스프링 데이터 JPA가 'SpringDataJpaMemberRepository'를 스프링 빈으로 자동 등록
//  JpaRepository<T,ID> ID:Entity 식별자 타입 
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

	//  JPQL : select m from Member m where m.name =?
	@Override
	Optional<Member> findByName(String name);
	
}
