package sujin.realtimetrip.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.chat.dto.ChatRequest;
import sujin.realtimetrip.chat.entity.Chat;
import sujin.realtimetrip.chat.entity.ChatRoom;
import sujin.realtimetrip.chat.enums.MessageType;
import sujin.realtimetrip.chat.repository.ChatRepository;
import sujin.realtimetrip.chat.repository.ChatRoomRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;
import sujin.realtimetrip.user.entity.User;
import sujin.realtimetrip.user.repository.UserRepository;


import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;
    private final ChannelTopic channelTopic;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public void sendMessage(ChatRequest chatRequest) {
        User user = userRepository.findById(chatRequest.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        ChatRoom chatRoom = chatRoomRepository.findById(chatRequest.getRoomId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        //채팅 생성 및 저장
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .user(user)
                .message(chatRequest.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        // chatRepository에 Chat 저장
        chatRepository.save(chat);
        // 채널 토픽 가져옴
        String topic = channelTopic.getTopic();

        // ChatMessageRequest에 유저정보 저장
        chatRequest.setNickName(user.getNickName());
        chatRequest.setUserId(user.getId());

        if (chatRequest.getType() == MessageType.TALK) {
            // 그륩 채팅일 경우
            redisTemplate.convertAndSend(topic, chatRequest);
        }
    }
}
