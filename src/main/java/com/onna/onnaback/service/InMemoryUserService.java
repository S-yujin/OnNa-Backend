package com.onna.onnaback.service;

import com.onna.onnaback.domain.User;
import com.onna.onnaback.dto.LoginRequest;
import com.onna.onnaback.dto.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 지금은 메모리 기반 유저 저장소.
 *
 * 🔜 나중에 DB 붙일 때:
 *   - UserRepository(JpaRepository<User, Long>) 만들고
 *   - 여기 로직을 Repository 호출로 교체하면 됨.
 */
@Service
@Slf4j
public class InMemoryUserService implements UserService {

    private final Map<Long, User> users = new HashMap<>();
    private final Map<String, User> usersByEmail = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    @Override
    public User signup(SignupRequest request) {
        if (usersByEmail.containsKey(request.getEmail())) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }

        User u = new User();
        u.setId(seq.incrementAndGet());
        u.setName(request.getName());
        u.setEmail(request.getEmail());
        u.setPassword(request.getPassword()); // 🔜 비밀번호 해시 자리
        u.setRole(request.getRole());

        users.put(u.getId(), u);
        usersByEmail.put(u.getEmail(), u);

        log.info("New user signed up: id={}, email={}, role={}", u.getId(), u.getEmail(), u.getRole());
        return u;
    }

    @Override
    public User login(LoginRequest request) {
        User u = usersByEmail.get(request.getEmail());
        if (u == null || !u.getPassword().equals(request.getPassword())) {
            throw new NoSuchElementException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        log.info("User login success: id={}, email={}", u.getId(), u.getEmail());
        return u;
    }

    @Override
    public User findById(Long id) {
        User u = users.get(id);
        if (u == null) throw new NoSuchElementException("User not found: " + id);
        return u;
    }
}
