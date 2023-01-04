package com.arton.backend.auth.adapter.in;

import com.arton.backend.auth.application.port.in.KaKaoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final KaKaoUseCase kaKaoUseCase;

    @GetMapping("/kakao")
    public ResponseEntity<String> getCode(@RequestParam String code){
        log.info("code {}", code);
        kaKaoUseCase.kakaoLogin(code);
        return ResponseEntity.ok(code);
    }
}
