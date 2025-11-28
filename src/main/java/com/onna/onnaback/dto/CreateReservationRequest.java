package com.onna.onnaback.dto;

import lombok.Data;

/**
 * 예약 생성 요청 DTO.
 *
 * 나중에 DB / 회원 테이블 붙을 때도 이 형식은 웬만하면 그대로 유지.
 */
@Data
public class CreateReservationRequest {

    // 어떤 클래스를 예약하는지
    private Long classId;

    // 어떤 유저가 예약하는지
    // 지금은 프론트에서 userId=1 고정으로 보내도 되고,
    // 나중에 로그인 붙이면 토큰에서 꺼내서 사용하면 됨.
    private Long userId;

    // 몇 명 예약하는지
    private int headCount;
}
