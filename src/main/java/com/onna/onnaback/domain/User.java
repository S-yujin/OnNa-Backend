package com.onna.onnaback.domain;

import jakarta.persistence.*; // JPA 어노테이션 임포트

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 유저 도메인
 *
 * ✅ JPA 엔티티로 변경
 */
@Entity // 이 클래스가 JPA 엔티티임을 명시
@Table(name = "users") // 매핑될 DB 테이블 이름 지정
@Data
@NoArgsConstructor // JPA는 기본 생성자가 필요
public class User {

    @Id // 기본 키(Primary Key) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID 자동 생성 (MySQL의 AUTO_INCREMENT와 매핑)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100) // 이메일은 중복 불가능
    private String email;

    @Column(nullable = false)
    private String password; // 🔜 실서비스에서는 비밀번호를 반드시 해시해서 저장해야 함.

    @Column(nullable = false, length = 20)
    private String role; // "MENTOR"/"MENTEE" 등
}