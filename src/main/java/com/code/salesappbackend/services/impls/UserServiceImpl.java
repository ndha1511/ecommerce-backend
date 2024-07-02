package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.UserDetail;
import com.code.salesappbackend.repositories.BaseRepository;
import com.code.salesappbackend.repositories.UserRepository;
import com.code.salesappbackend.services.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(BaseRepository<User, Long> repository,
                           UserRepository userRepository) {
        super(repository, User.class);
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new UserDetail(user);
    }
}
