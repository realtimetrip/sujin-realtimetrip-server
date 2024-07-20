package sujin.realtimetrip.user.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.user.dto.LoginDto;
import sujin.realtimetrip.user.dto.SignUpDto;
import sujin.realtimetrip.user.dto.UserDto;
import sujin.realtimetrip.user.service.UserService;
import sujin.realtimetrip.global.response.ApiResponse;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signUpUser(@ModelAttribute SignUpDto signUpDto) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.signUp(signUpDto)));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody LoginDto loginRequest, HttpServletResponse response) {

        // 이메일과 비밀번호가 일치하는 사용자 찾기
        UserDto loginUser = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // 이메일과 비밀번호가 일치하는 사용자가 없으면 에러 반환
        if (loginUser == null) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        // 로그인 성공 시, 사용자 ID를 포함하는 쿠키를 생성하고, HttpServletResponse에 추가
        // 쿠키는 HTTP-only로 설정되어 있으며, 만료 기간은 24시간
        Cookie cookie = new Cookie("userId", String.valueOf(loginUser.getUserId()));
        cookie.setHttpOnly(true); // JavaScript를 통한 접근 방지
        cookie.setMaxAge(24 * 60 * 60); // 쿠키의 만료 기간을 24시간으로 설정
        response.addCookie(cookie);

        return ResponseEntity.ok().body(ApiResponse.success(loginUser));
    }
}
