package com.onna.onnaback.repository;

import com.onna.onnaback.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository<엔티티 클래스, 기본 키 타입> 을 상속
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 User 엔티티를 찾는 쿼리 메소드 정의
    // Spring Data JPA가 메소드 이름 규칙에 따라 자동으로 SQL 쿼리를 생성함.
    Optional<User> findByEmail(String email);
}