package com.arton.backend.auth.application.port.in;

public interface KaKaoUseCase {
    LoginResponseDto kakaoLogin(String code);
}
