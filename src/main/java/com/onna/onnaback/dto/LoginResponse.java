package com.onna.onnaback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 로그인/회원가입 성공 후 프론트에 내려줄 최소 정보
 * (나중에 토큰 추가하면 여기에 accessToken 같은 거만 추가하면 됨)
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String name;
    private String role;
}
