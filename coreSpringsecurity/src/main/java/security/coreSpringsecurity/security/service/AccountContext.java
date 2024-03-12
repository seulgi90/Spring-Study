package security.coreSpringsecurity.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import security.coreSpringsecurity.domain.Account;

import java.util.Collection;

public class AccountContext extends User { // User는 UserDetails를 상속 받고 있음

    private final Account account;

    public AccountContext(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getUsername(), account.getPassword(),  authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
