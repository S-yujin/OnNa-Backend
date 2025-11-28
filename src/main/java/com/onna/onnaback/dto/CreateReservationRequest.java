package com.onna.onnaback.dto;

import lombok.Data;

/**
 * 예약 생성 요청 DTO.
 */
@Data
public class CreateReservationRequest {
    
    private Long classId;     // 원데이 클래스 ID (필수)
    private int headCount;    // 인원 수 (필수)
    private Long userId;      // 예약자 ID (선택: 현재는 프론트에서 임시로 넣어줌)
}