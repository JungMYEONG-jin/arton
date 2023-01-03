package com.arton.backend.auth.application.port.in;

import lombok.*;

/**
 * access_token
 * refresh_token
 *
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private Long refreshTokenExpiresIn;
}
