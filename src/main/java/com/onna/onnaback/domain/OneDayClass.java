// src/main/java/com/onna/onnaback/domain/OneDayClass.java

package com.onna.onnaback.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "oneday_class")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OneDayClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, length = 50)
    private String category; // 프론트엔드와 Service에서 'category'로 사용

    // location 필드명을 'region' 대신 'location'으로 통일 (프론트/백 통일)
    @Column(nullable = false, length = 50)
    private String location; // 프론트엔드와 Service에서 'region' 대신 'location'으로 사용

    @Column(nullable = false)
    private int price;

    private Double rating;

    @Column(nullable = false, length = 50)
    private String teacherName; // 임시: 강사 이름 필드 추가 (프론트에서 필요)

    private String thumbnailUrl; // 임시: 썸네일 URL 추가 (프론트에서 필요)
}