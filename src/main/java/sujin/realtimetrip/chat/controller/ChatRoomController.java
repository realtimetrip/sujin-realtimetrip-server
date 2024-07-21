package sujin.realtimetrip.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.chat.dto.ChatRoomDto;
import sujin.realtimetrip.chat.dto.CountryDto;
import sujin.realtimetrip.chat.service.ChatRoomService;
import sujin.realtimetrip.global.response.ApiResponse;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatService;

    // 채팅방 생성
    @PostMapping("/create-chatroom")
    public ResponseEntity<ApiResponse<List<CountryDto>>> insertCountriesAndChatRooms() {
        return ResponseEntity.ok().body(ApiResponse.success(chatService.insertCountriesAndChatRooms()));
    }

    // 채팅방 목록 조회
    @GetMapping("/chatroom")
    public ResponseEntity<ApiResponse<List<ChatRoomDto>>> getChatRoom() {
        return ResponseEntity.ok().body(ApiResponse.success(chatService.getChatRoom()));
    }
}
