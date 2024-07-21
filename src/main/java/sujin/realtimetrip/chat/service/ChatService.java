package sujin.realtimetrip.chat.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.chat.dto.ChatRequest;
import sujin.realtimetrip.chat.entity.Chat;
import sujin.realtimetrip.chat.entity.ChatRoom;
import sujin.realtimetrip.chat.entity.ChatRoomUser;
import sujin.realtimetrip.chat.enums.MessageType;
import sujin.realtimetrip.chat.repository.ChatRepository;
import sujin.realtimetrip.chat.repository.ChatRoomRepository;
import sujin.realtimetrip.chat.repository.ChatRoomUserRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;
import sujin.realtimetrip.user.entity.User;
import sujin.realtimetrip.user.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatRoomUserRepository chatUserRepository;
    private final ChannelTopic channelTopic;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Transactional
    public void sendMessage(ChatRequest chatRequest) {
        if (chatRequest.getType().equals(MessageType.TALK)) {
            // 채팅방에 입장했던 유저인지 확인
            ChatRoomUser chatUser = chatUserRepository.findByChatRoomIdAndUserId(chatRequest.getChatRoomId(), chatRequest.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            // 채팅방 불러오기
            ChatRoom chatRoom = chatRoomRepository.findById(chatRequest.getChatRoomId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

            //채팅 생성 및 저장
            Chat chat = Chat.builder()
                    .chatRoom(chatRoom)
                    .chatUser(chatUser)
                    .message(chatRequest.getMessage())
                    .createdAt(LocalDateTime.now())
                    .build();

            // chatRepository에 Chat 저장
            chatRepository.save(chat);
            // 채널 토픽 가져오기
            String topic = channelTopic.getTopic();

            // ChatMessageRequest에 유저정보 저장
            chatRequest.setUserId(chatUser.getUser().getId());

            // 메시지를 Redis를 통해 해당 토픽으로 전송
            redisTemplate.convertAndSend(topic, chatRequest);
        }
    }

    @Transactional
    public void enterUser(ChatRequest chatRequest) {
        if (chatRequest.getType().equals(MessageType.ENTER)) {
            // 이미 채팅방에 입장했던 유저인지 확인
            Optional<ChatRoomUser> chatUser = chatUserRepository.findByChatRoomIdAndUserId(chatRequest.getChatRoomId(), chatRequest.getUserId());

            if (chatUser.isPresent()) {
                throw new CustomException(ErrorCode.USER_ALREADY_IN_CHAT_ROOM);
            }

            // 채팅방 불러오기
            ChatRoom chatRoom = chatRoomRepository.findById(chatRequest.getChatRoomId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

            // user 객체 불러오기
            User user = userRepository.findById(chatRequest.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            // chatUser 객체 생성 및 저장
            ChatRoomUser newChatUser = new ChatRoomUser();
            newChatUser.setChatRoom(chatRoom);
            newChatUser.setUser(user);
            chatUserRepository.save(newChatUser);

            // 채널 토픽 가져오기
            String topic = channelTopic.getTopic();
            // 입장 메시지를 설정
            chatRequest.setMessage(user.getNickName() + " 님이 입장했습니다.");
            // 설정된 메시지를 Redis를 통해 해당 토픽으로 전송
            redisTemplate.convertAndSend(topic, chatRequest);
        }
    }
}
