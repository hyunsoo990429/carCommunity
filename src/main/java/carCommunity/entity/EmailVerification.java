package carCommunity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerification {

    @Id
    @Column(name = "Email", nullable = false, length = 255)
    private String email;

    @Column(name = "User_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "Verification_Code", nullable = false, length = 10)
    private String verificationCode;

    @Column(name = "Purpose", nullable = false, length = 10)
    private String purpose; // 'signup' or 'reset'

    @Column(name = "Created_At")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "Is_Verified")
    private Boolean isVerified = false;
}
