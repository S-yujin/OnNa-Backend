package com.onna.onnaback.controller;

import com.onna.onnaback.domain.Reservation;
import com.onna.onnaback.dto.CreateReservationRequest;
import com.onna.onnaback.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 예약 REST API 컨트롤러.
 *
 * URL 패턴은 전부 /api 아래로 맞춰서
 * - CORS 설정 (CorsConfig)에서 /api/** 한 번만 허용해 두면 됨.
 *
 * 나중에 DB, 인증 붙여도 이 "외부 계약(엔드포인트/JSON 형식)"은
 * 최대한 안 바꾸는 것이 목표.
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 예약 생성
     * POST /api/reservations
     *
     * Request Body (JSON):
     * {
     *   "classId": 1,
     *   "userId": 1,        // 지금은 더미 값, 나중에 토큰 기반으로 대체
     *   "headCount": 2
     * }
     */
    @PostMapping
    public Reservation create(@RequestBody CreateReservationRequest request) {
        return reservationService.createReservation(request);
    }

    /**
     * 내 예약 목록 조회
     * GET /api/reservations/my?userId=1
     *
     * 나중에 인증 붙이면 userId 파라미터 없이
     * SecurityContext 에서 꺼내는 방식으로 변경 예정.
     */
    @GetMapping("/my")
    public List<Reservation> myReservations(@RequestParam Long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    /**
     * 특정 클래스의 예약 목록 조회 (관리자/호스트용)
     * GET /api/reservations/class/{classId}
     */
    @GetMapping("/class/{classId}")
    public List<Reservation> reservationsByClass(@PathVariable Long classId) {
        return reservationService.getReservationsByClass(classId);
    }
}
