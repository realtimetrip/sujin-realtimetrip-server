package sujin.realtimetrip.config.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
// WebSocket 메시지 브로커 기능을 활성화합니다. 이는 애플리케이션에서 WebSocket을 사용하여 메시징 기능을 구현할 수 있게 해줍니다.
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 메시지를 구독할 때 사용할 경로를 "/sub"로 설정
        config.enableSimpleBroker("/sub");

        // 클라이언트가 메시지를 발행할 때 사용할 경로를 "/pub"로 설정
        // 이 경로를 통해 서버로 메시지를 보낸다.
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결을 위한 엔드포인트를 "/ws"로 설정
        registry.addEndpoint("/ws")
                // CORS(Cross-Origin Resource Sharing) 정책에서 모든 출처("*")로부터의 연결 허용
                .setAllowedOriginPatterns("*")
                // SockJS는 웹 브라우저에서 WebSocket을 지원하지 않을 경우를 대비한 대체 옵션을 제공한다.
                // SockJS 클라이언트를 사용하여 연결할 수 있도록 설정한다.
                .withSockJS();
    }
}
