package com.arton.backend.auth.application.port.in;

public interface KaKaoUseCase {
    TokenDto kakaoLogin(String code);
}
