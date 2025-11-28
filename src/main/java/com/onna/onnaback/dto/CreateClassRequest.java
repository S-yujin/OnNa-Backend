package com.onna.onnaback.dto;

import lombok.Data;

@Data
public class CreateClassRequest {

    private String title;
    private String description;
    private String category;
    private String location;

    // 문자열로 받아서 서비스에서 파싱
    private String date;       // "2025-12-01"
    private String startTime;  // "14:00"
    private String endTime;    // "16:00"

    private Integer capacity;
    private Integer price;
    private Long hostId;       // 시니어 유저 ID (임시)
}
