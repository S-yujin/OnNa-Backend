// src/main/java/com/onna/onnaback/controller/ClassController.java

package com.onna.onnaback.controller;

import com.onna.onnaback.domain.OneDayClass;
import com.onna.onnaback.dto.CreateClassRequest;
import com.onna.onnaback.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    /**
     * 클래스 목록 조회
     * GET /api/classes?region={지역}&category={카테고리}
     */
    @GetMapping
    public List<OneDayClass> list(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String category
    ) {
        // ClassService의 getClasses 메서드가 필터링 로직을 처리합니다.
        return classService.getClasses(region, category);
    }

    /**
     * 클래스 상세 조회
     * GET /api/classes/{id}
     */
    @GetMapping("/{id}")
    public OneDayClass detail(@PathVariable Long id) {
        return classService.getClassDetail(id);
    }

    /**
     * 클래스 생성
     * POST /api/classes
     */
    @PostMapping
    public OneDayClass create(@RequestBody CreateClassRequest request) {
        return classService.createClass(request);
    }
}