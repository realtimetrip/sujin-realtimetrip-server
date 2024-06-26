package sujin.realtimetrip.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.user.dto.SignUpDto;
import sujin.realtimetrip.user.dto.UserDto;
import sujin.realtimetrip.user.entity.User;
import sujin.realtimetrip.user.repository.UserRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    // 회원가입
    public UserDto signUp(SignUpDto signUpDto) {

        // 입력된 이메일로 가입된 유저가 있는지 확인
        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        // 이미 사용된 이메일인 경우 에러 반환
        if(!findUser.isEmpty())
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);

        // 입력된 이메일 및 정보로 회원가입
        User savedUser = userRepository.saveAndFlush(new User(signUpDto.getEmail(), signUpDto.getPassword(), signUpDto.getNickName()));

        // 회원가입된 유저 정보 반환
        return new UserDto(savedUser.getId(), savedUser.getEmail(), savedUser.getNickName());
    }

    // 로그인
    public User login(String email, String password) {
        // 이메일과 비밀번호가 일치하는 User 객체 반환
        return userRepository.findByEmail(email)
                .filter(m -> m.getPassword().equals(password)) // 비밀번호 일치 여부 확인
                .orElse(null); // 일치하는 사용자가 없을 경우 null 반환
    }
}
