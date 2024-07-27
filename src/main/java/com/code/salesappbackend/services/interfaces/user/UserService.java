package com.code.salesappbackend.services.interfaces.user;

import com.code.salesappbackend.dtos.requests.auth.ChangePasswordRequest;
import com.code.salesappbackend.dtos.responses.auth.LoginResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.user.User;
import com.code.salesappbackend.services.interfaces.BaseService;

public interface UserService extends BaseService<User, Long> {
    LoginResponse changePassword(ChangePasswordRequest changePasswordRequest) throws DataNotFoundException;
    User getUserByEmail(String email) throws DataNotFoundException;
}
