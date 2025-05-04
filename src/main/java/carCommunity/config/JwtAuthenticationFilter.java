package carCommunity.config;

// 서블릿 요청/응답 및 필터 관련 클래스
import jakarta.servlet.FilterChain; // 필터 체인을 통해 다음 필터로 요청 전달
import jakarta.servlet.ServletException; // 서블릿 처리 중 발생하는 예외
import jakarta.servlet.http.HttpServletRequest; // HTTP 요청 객체
import jakarta.servlet.http.HttpServletResponse; // HTTP 응답 객체

// Spring Security 관련 클래스들
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // 인증된 사용자 정보 저장 객체
import org.springframework.security.core.context.SecurityContextHolder; // 현재 보안 컨텍스트 관리
import org.springframework.security.core.userdetails.UserDetails; // 사용자 상세 정보 인터페이스
import org.springframework.security.core.userdetails.UserDetailsService; // 사용자 정보 로딩 서비스
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // 웹 요청 기반 인증 상세 정보 생성

// 컴포넌트 등록과 필터 상속 관련 클래스
import org.springframework.stereotype.Component; // 스프링 빈으로 등록
import org.springframework.web.filter.OncePerRequestFilter; // 요청당 1회 실행되는 필터 기본 클래스

import java.io.IOException; // 입출력 예외 처리

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 요청에서 JWT 토큰 추출
        String token = resolveToken(request);

        // 토큰이 존재하고 유효한 경우 인증 처리
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰에서 사용자 ID 추출
            String userId = jwtTokenProvider.getUserId(token);

            // 사용자 정보를 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

            // 인증 객체 생성 및 SecurityContext에 저장
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
