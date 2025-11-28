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
     */
    @PostMapping
    public Reservation create(@RequestBody CreateReservationRequest request) {
        return reservationService.createReservation(request);
    }

    /**
     * 특정 예약 상세 조회
     * GET /api/reservations/{id}
     */
    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable Long id) {
        // 💡 ID로 단일 예약 객체 조회 추가
        return reservationService.getReservationById(id);
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
     * 특정 클래스의 예약 목록 조회 (관리자용)
     * GET /api/reservations/class?classId=1
     */
    @GetMapping("/class")
    public List<Reservation> classReservations(@RequestParam Long classId) {
        return reservationService.getReservationsByClass(classId);
    }
}