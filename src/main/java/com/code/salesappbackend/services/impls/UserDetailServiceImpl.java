package com.code.salesappbackend.services.impls;

import com.code.salesappbackend.models.User;
import com.code.salesappbackend.models.UserDetail;
import com.code.salesappbackend.repositories.UserRepository;
import com.code.salesappbackend.services.interfaces.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new UserDetail(user);
    }
}
