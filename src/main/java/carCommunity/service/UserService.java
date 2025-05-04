package carCommunity.service;

import carCommunity.config.JwtTokenProvider;
import carCommunity.dto.*;
import carCommunity.entity.User;
import carCommunity.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final EmailVerificationService emailVerificationService;

    // 회원가입 처리
    @Transactional
    public void register(UserJoinRequest request) {
        if (userRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        // 인증 코드 확인
        //emailVerificationService.verifyAndMarkAsVerified(request.getEmail(), "signup");

        User user = User.builder()
                .userId(request.getUserId())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .name(request.getName())
                .build();

        userRepository.save(user);
    }

    // 로그인 처리
    public UserLoginResponse login(UserLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword())
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(request.getUserId());
        return new UserLoginResponse(token);
    }



    // 회원 정보 조회
    public UserResponse getUserInfo(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return new UserResponse(
                user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getPhone(),
                user.getName(),
                user.getCreatedAt()
        );
    }

    // 회원 정보 수정
    @Transactional
    public void updateUserInfo(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setNickname(request.getNickname());
        user.setPhone(request.getPhone());
        userRepository.save(user);
    }

        /*
    // 아이디 찾기
    public String findUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getUserId)
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 사용자가 없습니다."));
    }
     */

    /*
    // 비밀번호 재설정
    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        emailVerificationService.verifyAndMarkAsVerified(request.getEmail(), "reset");

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 사용자가 없습니다."));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
    */

    /*
    // 회원 탈퇴
    @Transactional
    public void withdraw(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        WithdrawnUser withdrawnUser = WithdrawnUser.builder()
                .userId(user.getUserId())
                .deletedAt(LocalDateTime.now())
                .build();

        withdrawnUserRepository.save(withdrawnUser);
        userRepository.delete(user);
    }
     */
}
