package sujin.realtimetrip.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sujin.realtimetrip.chat.dto.ChatRoom;
import sujin.realtimetrip.chat.service.ChatService;
import sujin.realtimetrip.global.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatroom")
public class ChatRoomController {
    private final ChatService chatService;

    // 채팅 리스트 화면
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ChatRoom>>> goChatRoom(){
        List<ChatRoom> chatRooms = chatService.findAllRoom();
        return ResponseEntity.ok(ApiResponse.success(chatRooms));
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ApiResponse<String>> createRoom(@RequestParam String name) {
        ChatRoom room = chatService.createChatRoom(name);
        return ResponseEntity.ok(ApiResponse.success(room.getRoomId()));
    }

    // 채팅에 참여한 유저 리스트 반환
    @GetMapping("/userlist")
    public ResponseEntity<ApiResponse<ArrayList<String>>> userList(String roomId) {
        return ResponseEntity.ok(ApiResponse.success(chatService.getUserList(roomId)));
    }
}
