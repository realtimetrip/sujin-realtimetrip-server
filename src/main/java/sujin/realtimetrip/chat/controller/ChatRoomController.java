package sujin.realtimetrip.chat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.chat.dto.ChatRoomRequest;
import sujin.realtimetrip.chat.service.ChatRoomService;
import sujin.realtimetrip.global.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {
    private final ChatRoomService chatService;

    // 채팅방 생성
    @PostMapping()
    public ResponseEntity<ApiResponse<Long>> createRoom(@RequestBody @Valid ChatRoomRequest chatRoomRequest) {
        return ResponseEntity.ok(ApiResponse.success(chatService.createChatRoom(chatRoomRequest.getCountryName())));
    }
}
