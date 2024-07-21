package sujin.realtimetrip.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.chat.dto.ChatRequest;
import sujin.realtimetrip.chat.dto.ChatResponse;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;
import sujin.realtimetrip.user.entity.User;
import sujin.realtimetrip.user.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    // 객체 매핑 및 Redis, WebSocket 템플릿 주입
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;
    private final UserRepository userRepository;

    // Redis에서 메시지 수신 시 호출
    @Override
    public void onMessage(final Message message, final byte[] pattern) {
        try {
            // Redis 메시지를 문자열로 역직렬화
            String publishMessage = redisTemplate.getStringSerializer().deserialize(message.getBody());
            // 역직렬화된 문자열을 ChatMessageRequest 객체로 변환
            ChatRequest roomMessage = objectMapper.readValue(publishMessage, ChatRequest.class);

            User user = userRepository.findById(roomMessage.getUserId())
                    .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            // ChatMessageRequest 객체를 GetChatMessageResponse 객체로 변환
            ChatResponse chatMessageResponse = new ChatResponse(roomMessage, user.getNickName());

            // 특정 WebSocket 경로로 메시지 전송
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getChatRoomId(), chatMessageResponse);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CHAT_MESSAGE_NOT_FOUNT);
        }
    }
}