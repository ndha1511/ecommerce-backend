package com.code.salesappbackend.services.interfaces;

import com.code.salesappbackend.dtos.requests.ChangePasswordRequest;
import com.code.salesappbackend.dtos.responses.LoginResponse;
import com.code.salesappbackend.exceptions.DataNotFoundException;
import com.code.salesappbackend.models.User;

public interface UserService extends BaseService<User, Long>{
    LoginResponse changePassword(ChangePasswordRequest changePasswordRequest) throws DataNotFoundException;
    User getUserByEmail(String email) throws DataNotFoundException;
}
