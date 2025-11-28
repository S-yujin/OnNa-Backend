// src/main/java/com/onna/onnaback/repository/ClassRepository.java

package com.onna.onnaback.repository;

import com.onna.onnaback.domain.OneDayClass;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClassRepository extends JpaRepository<OneDayClass, Long> {
    
    // 카테고리와 지역(location)으로 필터링
    List<OneDayClass> findByCategoryAndLocation(String category, String location);
    
    // 카테고리로만 필터링
    List<OneDayClass> findByCategory(String category);
    
    // 지역(location)으로만 필터링
    List<OneDayClass> findByLocation(String location);
}