package com.onna.onnaback.controller;

import com.onna.onnaback.domain.User;
import com.onna.onnaback.dto.LoginRequest;
import com.onna.onnaback.dto.LoginResponse;
import com.onna.onnaback.dto.SignupRequest;
import com.onna.onnaback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증 관련 REST 컨트롤러
 *
 * 지금은 인메모리 UserService를 사용해서
 *  - 회원가입: /api/auth/signup
 *  - 로그인:   /api/auth/login
 * 을 처리한다.
 *
 * 나중에 DB + JWT로 교체할 때도 URL/요청/응답 구조는 그대로 유지하는 게 목표.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody SignupRequest request) {
        User u = userService.signup(request);
        return ResponseEntity.ok(new LoginResponse(
                u.getId(),
                u.getName(),
                u.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        User u = userService.login(request);
        return ResponseEntity.ok(new LoginResponse(
                u.getId(),
                u.getName(),
                u.getRole()
        ));
    }

    ;
}