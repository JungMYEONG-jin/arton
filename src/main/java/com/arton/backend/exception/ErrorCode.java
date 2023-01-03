package com.arton.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USER_IS_EXIST(409, "USER_IS_EXIST", "이미 존재하는 유저입니다."),
    PARAMETER_NOT_VALID(400, "PARAMETER_NOT_VALID", "유효하지 않은 입력값입니다."),
    USER_NOT_FOUND(404, "COMMON-ERR-404", "사용자를 찾을 수 없습니다"),
    LOGIN_INFO_NOT_MATCHED(400, "LOGIN-ERR-400", "아이디 또는 패스워드가 틀립니다."),
    USER_NOT_AUTHORITY(403, "COMMON-ERR-500", "권한이 없습니다."),
    REFRESH_TOKEN_INVALID(498, "REFRESH_TOKEN_INVALID", "The Refresh Token is invalid"),
    TOKEN_INVALID(498, "TOKEN_INVALID", "The Token is invalid");
    private int status;
    private String errorCode;
    private String message;
}
