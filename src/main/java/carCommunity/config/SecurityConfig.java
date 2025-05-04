package carCommunity.config;

// Lombok 애너테이션으로 생성자 자동 생성
import lombok.RequiredArgsConstructor;

// 스프링 설정 관련 애너테이션
import org.springframework.context.annotation.Bean; // @Bean 어노테이션으로 빈 등록
import org.springframework.context.annotation.Configuration; // 설정 클래스 표시

// Spring Security 관련 클래스
import org.springframework.security.authentication.AuthenticationManager; // 인증 매니저
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // 인증 설정 참조
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // HTTP 보안 설정
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; // 웹 보안 활성화
import org.springframework.security.config.http.SessionCreationPolicy; // 세션 정책 설정
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 비밀번호 암호화 방식
import org.springframework.security.crypto.password.PasswordEncoder; // 암호화 인터페이스
import org.springframework.security.web.SecurityFilterChain; // 보안 필터 체인 설정
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // 인증 필터 위치 설정용

@Configuration // 이 클래스는 설정 클래스임을 나타냄
@EnableWebSecurity // Spring Security 웹 보안 활성화
@RequiredArgsConstructor // final 필드에 대해 생성자 자동 생성
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // JWT 인증 필터 주입

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable() // CSRF 보안 비활성화 (API에서는 일반적으로 사용 안함)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/users", "/users/login", "/email/**").permitAll() // 비회원 접근 허용 경로 (logout 제외)
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 BCrypt 사용
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager(); // AuthenticationManager 빈 등록
    }
}
