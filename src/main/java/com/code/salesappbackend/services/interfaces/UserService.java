package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User, Long>, UserDetailsService {
}
