package carCommunity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String userId;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;

    @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String phone;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    //@NotBlank(message = "인증 코드는 필수입니다.")
    //private String verificationCode;
}
