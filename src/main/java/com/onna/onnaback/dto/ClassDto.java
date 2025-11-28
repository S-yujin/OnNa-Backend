package com.onna.onnaback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDto {
    private Long id;
    private String title;        // 수업 제목
    private String teacher;      // 선생님 이름
    private String region;       // 지역(부산 사하구 등)
    private String category;     // 카테고리(요리/공예 등)
    private int price;           // 가격
    private String thumbnailUrl; // 썸네일 이미지
    private String shortDesc;    // 한 줄 설명
}
