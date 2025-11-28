package com.onna.onnaback.service;

import com.onna.onnaback.domain.Reservation;
import com.onna.onnaback.dto.CreateReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 메모리에만 저장하는 임시 예약 서비스.
 *
 * 🔹 역할
 * - 해커톤 / 개발 초기 단계에서 DB 없이도 API 구조를 테스트하기 위한 용도
 * - 나중에 DB 붙일 때는 이 클래스 대신 JPA 기반 구현으로 교체하면 됨.
 */
@Service
@Slf4j
public class InMemoryReservationService implements ReservationService {

    // 💡 클래스의 현재 예약 인원을 저장하는 맵 (Class ID -> Current Count)
    private final Map<Long, Integer> currentCounts = new HashMap<>();

    // id -> Reservation 저장
    private final Map<Long, Reservation> store = new HashMap<>();
    private final AtomicLong seq = new AtomicLong(0);

    // 💡 초기화: 클래스 1번의 인원수를 0으로 시작 (테스트 용)
    public InMemoryReservationService() {
        currentCounts.put(1L, 0);
    }

    @Override
    public Reservation createReservation(CreateReservationRequest request) {
        Long classId = request.getClassId();
        int headCount = request.getHeadCount();

        // 💡 예약 시 클래스 인원 업데이트 로직
        currentCounts.compute(classId, (key, count) -> (count == null ? 0 : count) + headCount);

        Reservation r = new Reservation();
        r.setId(seq.incrementAndGet());
        r.setClassId(classId);
        r.setUserId(
                request.getUserId() != null
                        ? request.getUserId()
                        : 1L // TODO: 나중에 인증 붙으면 실제 로그인 유저 ID로 교체
        );
        r.setHeadCount(request.getHeadCount());
        r.setReservedAt(LocalDateTime.now());

        store.put(r.getId(), r);

        log.info("New reservation created: {} (Class {} new count: {})", r, classId, currentCounts.get(classId));
        return r;
    }

    @Override
    public Reservation getReservationById(Long id) {
        // 💡 ID로 예약 객체를 찾아서 반환
        return store.get(id);
    }


    @Override
    public List<Reservation> getReservationsByUser(Long userId) {
        return store.values().stream()
                .filter(r -> Objects.equals(r.getUserId(), userId))
                .sorted(Comparator.comparing(Reservation::getReservedAt).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> getReservationsByClass(Long classId) {
        return store.values().stream()
                .filter(r -> Objects.equals(r.getClassId(), classId))
                .sorted(Comparator.comparing(Reservation::getReservedAt).reversed())
                .collect(Collectors.toList());
    }
}