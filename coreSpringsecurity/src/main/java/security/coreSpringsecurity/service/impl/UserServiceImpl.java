package security.coreSpringsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.coreSpringsecurity.domain.Account;
import security.coreSpringsecurity.repository.UserRepository;
import security.coreSpringsecurity.service.UserService;

import javax.transaction.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {
        userRepository.save(account);

    }
}
