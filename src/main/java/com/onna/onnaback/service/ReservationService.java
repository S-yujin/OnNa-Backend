package com.onna.onnaback.service;

import com.onna.onnaback.domain.Reservation;
import com.onna.onnaback.dto.CreateReservationRequest;

import java.util.List;

/**
 * 예약 관련 비즈니스 로직 인터페이스.
 *
 * 지금은 InMemory 구현을 쓰고,
 * 나중에 JPA/DB 구현(ReservationServiceJpa 같은 것)으로 대체할 예정.
 */
public interface ReservationService {

    // 예약 생성
    Reservation createReservation(CreateReservationRequest request);

    // 💡 추가: ID로 단일 예약 조회
    Reservation getReservationById(Long id);

    // 특정 유저의 예약 목록 조회
    List<Reservation> getReservationsByUser(Long userId);

    // 특정 클래스의 예약 목록 조회 (선택 기능)
    List<Reservation> getReservationsByClass(Long classId);
}