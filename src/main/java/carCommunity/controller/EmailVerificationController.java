package carCommunity.controller;

import carCommunity.dto.EmailVerificationRequest;
import carCommunity.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    // 이메일 인증 코드 전송 (회원가입 또는 비밀번호 재설정)
    @PostMapping
    public ResponseEntity<String> sendVerificationCode(@RequestBody @Valid EmailVerificationRequest request) {
        emailVerificationService.sendVerificationCode(request.getEmail(), request.getPurpose());
        return ResponseEntity.ok("인증 코드가 전송되었습니다.");
    }

    // 이메일 인증 코드 검증
    @PostMapping("/verify")
    public ResponseEntity<String> verifyCode(@RequestBody @Valid EmailVerificationRequest request) {
        emailVerificationService.verifyAndMarkAsVerified(request.getEmail(), request.getPurpose());
        return ResponseEntity.ok("인증 코드가 확인되었습니다.");
    }
}
