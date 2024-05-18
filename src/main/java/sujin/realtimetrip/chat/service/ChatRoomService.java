package sujin.realtimetrip.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.chat.entity.ChatRoom;
import sujin.realtimetrip.chat.repository.ChatRoomRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    /// 나라 이름으로 채팅방 만들기
    public Long createChatRoom(String countryName) {
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByCountryName(countryName);
        if (existingRoom.isPresent()) {
            throw new CustomException(ErrorCode.CHAT_ROOM_ALREADY_EXISTS);
        }
        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(countryName));

        return chatRoom.getId();
    }

}