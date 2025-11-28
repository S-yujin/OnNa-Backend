package com.onna.onnaback.service;

import com.onna.onnaback.domain.User;
import com.onna.onnaback.dto.LoginRequest;
import com.onna.onnaback.dto.SignupRequest;
import com.onna.onnaback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary; // 우선 순위 지정
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 데이터 변경 작업에 트랜잭션 적용
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
@Primary // 두 개 이상의 UserService 구현체 중 이 클래스를 우선 사용하도록 지정
public class DbUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional // DB에 저장하는 작업은 트랜잭션 안에서 수행
    public User signup(SignupRequest request) {
        // 1. 이메일 중복 검사
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        // 2. User 엔티티 생성
        User u = new User();
        // ID는 @GeneratedValue로 자동 생성되므로 따로 설정할 필요 없음
        u.setName(request.getName());
        u.setEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        u.setPassword(encodedPassword);
        u.setRole(request.getRole());

        // 3. DB에 저장 (UserRepository가 INSERT SQL 실행)
        User savedUser = userRepository.save(u);

        log.info("New user signed up: id={}, email={}, role={}", savedUser.getId(), savedUser.getEmail(), savedUser.getRole());
        return savedUser;
    }

    @Override
    @Transactional(readOnly = true) // 읽기 전용 작업
    public User login(LoginRequest request) {
        String emailToSearch = request.getEmail().trim();
        // 1. 이메일로 사용자 찾기 (UserRepository가 SELECT SQL 실행)
        User u = userRepository.findByEmail(emailToSearch)
                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 올바르지 않습니다."));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), u.getPassword())) {
        throw new NoSuchElementException("이메일 또는 비밀번호가 올바르지 않습니다.");
    }

        log.info("User login success: id={}, email={}", u.getId(), u.getEmail());
        return u;
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }
}