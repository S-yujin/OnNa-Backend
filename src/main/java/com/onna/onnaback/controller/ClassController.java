package com.onna.onnaback.controller;

import com.onna.onnaback.domain.OneDayClass;
import com.onna.onnaback.dto.CreateClassRequest;
import com.onna.onnaback.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 클래스 관련 REST API 컨트롤러.
 *
 * URL / HTTP 메서드 / 요청·응답 형식 = 프론트와 약속(외부 계약)
 * → 나중에 DB(JPA) 붙여도 최대한 이 코드는 그대로 두는 게 목표.
 */

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    /**
     * 클래스 목록 조회
     * GET /api/classes?region=&category=
     *
     * 지금은 도메인 객체(OneDayClass) 리스트를 그대로 반환.
     * 나중에 DTO 쓰고 싶으면 반환 타입만 바꾸고 매핑만 추가하면 됨.
     */
    @GetMapping
    public List<OneDayClass> list(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String category
    ) {
        return classService.getClasses(region, category);
    }

    /**
     * 클래스 상세 조회
     * GET /api/classes/{id}
     *
     * 지금은 못 찾으면 서비스에서 예외 던짐 → 스프링 기본 500 에러.
     * 나중에 404로 바꾸고 싶으면 @ControllerAdvice 에서 공통 처리하거나,
     * 여기서 ResponseEntity로 감싸는 방식으로 확장 가능.
     */
    @GetMapping("/{id}")
    public OneDayClass detail(@PathVariable Long id) {
        return classService.getClassDetail(id);
    }

    /**
     * 클래스 생성
     * POST /api/classes
     *
     * 지금은 요청 DTO(CreateClassRequest)를 받아서
     * 도메인(OneDayClass)을 만들고 그대로 반환.
     * 나중에 DB 붙으면 JPA로 저장해서 반환하게 바뀔 예정.
     */
    @PostMapping
    public OneDayClass create(@RequestBody CreateClassRequest request) {
        return classService.createClass(request);
    }

    // ============================
    //   💾 나중에 DB 붙일 때 요약
    // ============================
    // 1) OneDayClass 에 @Entity, @Id 등 추가
    // 2) OneDayClassRepository extends JpaRepository<OneDayClass, Long> 생성
    // 3) InMemoryClassService 대신 JpaClassService 만들어서
    //    - getClasses / getClassDetail / createClass 안에서
    //      repository.findAll(), findById(), save() 사용
    // 컨트롤러 코드는 그대로 두고 서비스 구현체만 갈아끼우는 구조로 유지.
}
