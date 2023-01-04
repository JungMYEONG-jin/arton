package com.arton.backend.auth.application.service;

import com.arton.backend.auth.application.port.in.KaKaoUseCase;
import com.arton.backend.auth.application.port.in.TokenDto;
import com.arton.backend.user.adapter.out.repository.UserRepository;
import com.arton.backend.user.domain.AgeRange;
import com.arton.backend.user.domain.Gender;
import com.arton.backend.user.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class KaKaoService implements KaKaoUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Value("${kakao.client.id}")
    private String clientId;
    @Value("${kakao.redirect.url}")
    private String redirectURL;

    @Override
    public TokenDto kakaoLogin(String code) {
        String accessToken = getAccessToken(code, redirectURL);
        User register = register(accessToken);
        // token 발행 필요.
        return null;
    }

    /**
     * https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
     * 토근 받기 참조
     * @param code
     * @param uri
     * @return
     */
    private String getAccessToken(String code, String uri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant-type", "authorization_code");
        body.add("client-id", clientId);
        body.add("redirect_uri", uri);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class);
        String responseBody = response.getBody();
        try {
            return objectMapper.readTree(responseBody).get("access_token").asText();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 동의한 데이터 가져오기
     *
     * @param accessToken
     * @return
     */
    private JsonNode getUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+accessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/v2/user/me",
                HttpMethod.GET,
                request,
                String.class);

        String responseBody = response.getBody();
        try {
            return objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 카카오로 회원가입된 계정이 있으면 해당 유저 정보 return
     * 아니면 회원가입 진행 + return
     * @param accessToken
     * @return
     */
    private User register(String accessToken) {
        JsonNode userInfo = getUserInfo(accessToken);
        long id = userInfo.get("id").asLong();
        User user = userRepository.findByKakaoId(id).orElse(null);
        if (user == null) {
            String nickName = userInfo.get("kakao_account").get("profile").get("nickname").asText();
            String email = userInfo.get("kakao_account").get("email").asText();
            String ageRange = userInfo.get("kakao_account").get("age_range").asText();
            // age Range example 20~29
            // 앞자리만 가져와서 해결하자
            int age = Integer.parseInt(ageRange.substring(0, 1));
            String gender = userInfo.get("kakao_account").get("gender").asText();
            /** password random */
            String password = UUID.randomUUID().toString();
            user = User.builder().email(email)
                    .gender(Gender.get(gender.toUpperCase(Locale.ROOT)))
                    .password(passwordEncoder.encode(password))
                    .kakaoId(id)
                    .nickname(nickName)
                    .profileImageUrl("/image/profiles/default.png")
                    .ageRange(AgeRange.get(age))
                    .build();
            userRepository.save(user);
        }
        return user;
    }


}
