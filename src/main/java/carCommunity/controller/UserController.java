package carCommunity.controller;

import carCommunity.dto.PasswordResetRequest;
import carCommunity.dto.UserJoinRequest;
import carCommunity.dto.UserLoginRequest;
import carCommunity.dto.UserLoginResponse;
import carCommunity.dto.UserResponse;
import carCommunity.dto.UserUpdateRequest;
import carCommunity.service.EmailVerificationService;
import carCommunity.service.UserService;
import carCommunity.config.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailVerificationService emailVerificationService;

    // 회원가입
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid UserJoinRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    // 회원 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserInfo(@PathVariable("id") String userId) {
        return ResponseEntity.ok(userService.getUserInfo(userId));
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserInfo(@PathVariable("id") String userId,
                                               @RequestBody @Valid UserUpdateRequest request) {
        userService.updateUserInfo(userId, request);
        return ResponseEntity.ok().build();
    }

    /*
    // 아이디 찾기
    @GetMapping("/find-id")
    public ResponseEntity<String> findUserId(@RequestParam String email) {
        return ResponseEntity.ok(userService.findUserIdByEmail(email));
    }
     */

    /*
    // 비밀번호 재설정 요청 - 인증 코드 전송만 수행
    @PostMapping("/help/pw")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        emailVerificationService.sendVerificationCode(email, "reset");
        return ResponseEntity.ok("비밀번호 재설정 코드 전송 완료");
    }
     */

    /*
    // 비밀번호 재설정 실행 (코드 검증 포함)
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
    */

    /*
    // 회원 탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestHeader("Authorization") String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", ""));
        userService.withdraw(userId);
        return ResponseEntity.ok().build();
    }
    */
}
