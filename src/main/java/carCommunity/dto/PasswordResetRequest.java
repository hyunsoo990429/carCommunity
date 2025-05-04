package carCommunity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {

    @Email(message = "유효한 이메일을 입력하세요.")
    private String email;

    @NotBlank(message = "인증 코드는 필수입니다.")
    private String verificationCode;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String newPassword;
}
