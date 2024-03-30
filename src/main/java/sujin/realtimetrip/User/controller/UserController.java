package sujin.realtimetrip.User.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.User.dto.SignUpDto;
import sujin.realtimetrip.User.dto.UserDto;
import sujin.realtimetrip.User.service.UserService;
import sujin.realtimetrip.util.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserDto>> signUpUser(@Valid @RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok().body(ApiResponse.success(userService.signUp(signUpDto)));
    }
}
