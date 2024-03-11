package security.coreSpringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.coreSpringsecurity.domain.Account;

public interface UserRepository extends JpaRepository<Account, Long> {
}
