package carCommunity.repository;

import carCommunity.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {

    Optional<EmailVerification> findTopByEmailAndVerificationCodeAndPurposeOrderByCreatedAtDesc(
            String email, String verificationCode, String purpose);

    Optional<EmailVerification> findTopByEmailAndPurposeOrderByCreatedAtDesc(
            String email, String purpose);
}
