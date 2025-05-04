package carCommunity.service;

import carCommunity.entity.EmailVerification;
import carCommunity.repository.EmailVerificationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    /**
     * 인증 코드 전송 처리
     * 이메일, 목적(signup/reset)에 따라 인증 코드 생성 및 발송
     */
    public void sendVerificationCode(String email, String purpose) {
        String code = generateVerificationCode();

        // DB에 인증 코드 저장
        EmailVerification verification = EmailVerification.builder()
                .email(email)
                .verificationCode(code)
                .purpose(purpose)
                .isVerified(false)
                .build();
        emailVerificationRepository.save(verification);

        // 이메일 발송
        try {
            sendEmail(email, "[CarCommunity] 인증 코드", "인증 코드는 다음과 같습니다: <b>" + code + "</b>");
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 발송에 실패했습니다.", e);
        }
    }

    /**
     * 인증 코드 검증 처리
     * 이메일 + 목적에 해당하는 가장 최근 인증 내역을 확인하여 인증 상태 변경
     */
    @Transactional
    public void verifyAndMarkAsVerified(String email, String purpose) {
        EmailVerification verification = emailVerificationRepository
                .findTopByEmailAndPurposeOrderByCreatedAtDesc(email, purpose)
                .orElseThrow(() -> new IllegalArgumentException("인증 요청 기록이 없습니다."));

        verification.setIsVerified(true); // 인증 완료 표시
    }

    // 6자리 숫자 코드 생성
    private String generateVerificationCode() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    // 이메일 발송
    private void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
