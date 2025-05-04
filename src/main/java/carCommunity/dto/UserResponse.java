package carCommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponse {

    private String userId;
    private String nickname;
    private String email;
    private String phone;
    private String name;
    private LocalDateTime createdAt;
}
