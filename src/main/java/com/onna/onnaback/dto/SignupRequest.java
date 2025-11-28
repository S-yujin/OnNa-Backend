package com.onna.onnaback.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String role; // "student" / "teacher" 같은 값
}
