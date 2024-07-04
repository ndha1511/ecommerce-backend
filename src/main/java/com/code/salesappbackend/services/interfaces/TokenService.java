package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.models.Token;
import com.code.salesappbackend.models.User;

public interface TokenService {
    void saveToken(User user, Token token);
}
