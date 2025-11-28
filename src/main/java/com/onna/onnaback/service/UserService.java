package com.onna.onnaback.service;

import com.onna.onnaback.domain.User;
import com.onna.onnaback.dto.LoginRequest;
import com.onna.onnaback.dto.SignupRequest;

public interface UserService {

    User signup(SignupRequest request);

    User login(LoginRequest request);

    User findById(Long id);
}
