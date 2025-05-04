package carCommunity;

import carCommunity.dto.*;
import carCommunity.entity.User;
import carCommunity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private final String testEmail = "test@example.com";

    @BeforeEach
    void cleanup() {
        userRepository.deleteById("testUser");
    }

    @Test
    void 회원가입_로그인_정보수정_비밀번호변경_전체흐름() throws Exception {
        // 1. 이메일 인증 코드 전송 (회원가입)
        mockMvc.perform(post("/auth/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"" + testEmail + "\", \"purpose\": \"signup\"}"))
                .andExpect(status().isOk());

        // 2. 회원가입
        UserJoinRequest joinRequest = new UserJoinRequest();
        joinRequest.setUserId("testUser");
        joinRequest.setNickname("테스트닉");
        joinRequest.setPassword("testpassword");
        joinRequest.setEmail(testEmail);
        joinRequest.setPhone("01012345678");
        joinRequest.setName("홍길동");
        joinRequest.setVerificationCode("123456"); // 테스트용 고정 코드 가정

        mockMvc.perform(post("/auth/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isOk());

    }
}
