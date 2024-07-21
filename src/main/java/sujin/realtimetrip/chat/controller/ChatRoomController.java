package sujin.realtimetrip.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.chat.dto.*;
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

    // 채팅방 유저 목록 조회
    @GetMapping("/chatroom/{chatroomId}/users")
    public ResponseEntity<ApiResponse<List<ChatRoomUserDto>>> getChatRoomUser(@PathVariable String  chatroomId) {
        return ResponseEntity.ok().body(ApiResponse.success(chatService.getChatRoomUsers(chatroomId)));
    }

    // 채팅 내역 조회
    @GetMapping("/chatroom/{chatroomId}/messages")
    public ResponseEntity<ApiResponse<ChatRoomMessagesDto>> getChatMessages(
            @PathVariable String chatroomId,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam Long chatId) {
        return ResponseEntity.ok(ApiResponse.success(chatService.getChatRoomMessages(chatroomId, size, chatId)));
    }
}
