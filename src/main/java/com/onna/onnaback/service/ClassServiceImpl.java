// src/main/java/com/onna/onnaback/service/ClassServiceImpl.java

package com.onna.onnaback.service;

import com.onna.onnaback.domain.OneDayClass;
import com.onna.onnaback.dto.CreateClassRequest;
import com.onna.onnaback.repository.ClassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 클래스 관련 비즈니스 로직 구현체 (JPA DB 연동)
 */
@Service
@RequiredArgsConstructor
@Primary // InMemoryService가 있다면 이 구현체를 우선 사용하도록 지정
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    /**
     * 클래스 목록 조회 (필터링 지원)
     * region과 category 매개변수를 사용하여 DB에서 직접 조회합니다.
     * DB 쿼리를 통해 필터링하는 것이 Java Stream 필터링보다 성능상 훨씬 효율적입니다.
     */
    @Override
    public List<OneDayClass> getClasses(String region, String category) {
        
        // 1. region(location)과 category 모두 있는 경우
        if (region != null && category != null) {
            return classRepository.findByCategoryAndLocation(category, region);
        }
        
        // 2. category만 있는 경우
        if (category != null) {
            return classRepository.findByCategory(category);
        }
        
        // 3. region(location)만 있는 경우
        if (region != null) {
            return classRepository.findByLocation(region);
        }

        // 4. 필터 조건이 없으면 전체 클래스 반환
        return classRepository.findAll();
    }

    /**
     * 클래스 상세 조회
     */
    @Override
    public OneDayClass getClassDetail(Long id) {
        // ID로 클래스를 찾고, 없으면 예외 발생
        return classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("클래스를 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 클래스 생성 (현재는 임시로 구현)
     */
    @Override
    public OneDayClass createClass(CreateClassRequest request) {
        // 실제 저장 로직
        OneDayClass newClass = OneDayClass.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .location(request.getLocation()) // region 대신 location 사용
                .price(0) // DTO에 price 필드가 없으므로 임시 값 설정
                .teacherName("임시 강사")
                .thumbnailUrl("임시 URL")
                .build();

        return classRepository.save(newClass);
    }
}