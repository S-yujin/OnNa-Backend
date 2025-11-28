// src/main/java/com/onna/onnaback/service/ClassService.java

package com.onna.onnaback.service;

import com.onna.onnaback.domain.OneDayClass;
import com.onna.onnaback.dto.CreateClassRequest;

import java.util.List;

/**
 * 클래스 관련 비즈니스 로직 인터페이스
 */
public interface ClassService {
    
    /**
     * 클래스 목록 조회 (필터링 지원)
     * @param region 지역 필터 (null이면 전체)
     * @param category 카테고리 필터 (null이면 전체)
     * @return 필터링된 클래스 목록
     */
    List<OneDayClass> getClasses(String region, String category);
    
    /**
     * 클래스 상세 조회
     * @param id 클래스 ID
     * @return 클래스 상세 정보
     */
    OneDayClass getClassDetail(Long id);
    
    /**
     * 클래스 생성
     * @param request 클래스 생성 요청 DTO
     * @return 생성된 클래스
     */
    OneDayClass createClass(CreateClassRequest request);
}