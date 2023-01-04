package com.arton.backend.infra.config;

import com.arton.backend.infra.jwt.JwtAccessDeniedHandler;
import com.arton.backend.infra.jwt.JwtAuthenticationEntryPoint;
import com.arton.backend.infra.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final RedisTemplate redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**", "/favicon.ico", "/css/**", "/js/**", "/img/**", "/lib/**");
    }

    /**
     * 나중에 배포할때 적용하자
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedOrigin("https://localhost:3000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * 1/4 현재 개발중이므로 cors 그냥 disable 해놓자
     * 추후 배포에 cors 적용
     * requestMatchers(CorsUtils::isPreFlightRequest).permitAll() xhr fail되면 이거 적용
     * @param http
     * @return
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/auth/signup", "/auth/login", "/auth/reissue", "/auth/kakao").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfig(tokenProvider, redisTemplate));
        return http.build();
    }
}
