package com.code.salesappbackend.services.interfaces.auth;

import com.code.salesappbackend.models.auth.Token;
import com.code.salesappbackend.models.user.User;

public interface TokenService {
    void saveToken(User user, Token token);
}
