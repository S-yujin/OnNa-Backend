package com.onna.onnaback.domain;

import lombok.Data;

/**
 * 유저 도메인
 *
 * ✅ 지금은 메모리 저장용 POJO
 * 🔜 나중에 DB(JPA) 붙일 때:
 *   - @Entity
 *   - @Table(name = "users")
 *   - @Id, @GeneratedValue 등 추가
 */
@Data
public class User {

    private Long id;        // 🔜 @Id, @GeneratedValue
    private String name;
    private String email;
    private String password; // 지금은 평문 (실서비스면 반드시 해시)
    private String role;     // "MENTOR"/"MENTEE" 또는 "TEACHER"/"STUDENT" 등
}
