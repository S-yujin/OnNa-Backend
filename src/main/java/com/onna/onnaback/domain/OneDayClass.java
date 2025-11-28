package com.onna.onnaback.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class OneDayClass {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer capacity;
    private Integer price;

    private Long hostId;   // 시니어 유저 ID (임시)
}
