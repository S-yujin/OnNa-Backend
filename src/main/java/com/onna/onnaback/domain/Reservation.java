package com.onna.onnaback.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 예약 도메인.
 *
 * 지금은 JPA 안 쓰고, 나중에 @Entity 붙여서 테이블에 매핑하면 됨.
 */
@Data
public class Reservation {

    private Long id;          // 예약 ID
    private Long classId;     // 원데이 클래스 ID
    private Long userId;      // 예약자(회원) ID  - 지금은 더미로 1L만 사용해도 됨
    private int headCount;    // 인원 수
    private LocalDateTime reservedAt; // 예약 생성 시각
}