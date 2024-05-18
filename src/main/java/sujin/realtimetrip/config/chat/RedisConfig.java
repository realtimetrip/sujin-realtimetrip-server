package sujin.realtimetrip.config.chat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import sujin.realtimetrip.chatting.RedisSubscriber;

@Configuration
public class RedisConfig {
    // Redis 메시지 리스너 컨테이너 설정
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory connectionFactory, // Redis 연결 팩토리
            MessageListenerAdapter listenerAdapter, // Redis 메시지를 처리할 리스너 어댑터
            ChannelTopic channelTopic // 구독할 채널 주제
    ) {
        // RedisMessageListenerContainer 인스턴스 생성
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // Redis 연결 팩토리 설정
        container.setConnectionFactory(connectionFactory);
        // 메시지 리스너와 채널을 컨테이너에 추가
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }

    // 메시지 리스너 어댑터 설정
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) {
        // RedisSubscriber 인스턴스를 사용하여 MessageListenerAdapter 인스턴스 생성
        return new MessageListenerAdapter(subscriber, "onMessage");
    }

    // RedisTemplate 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate
            (RedisConnectionFactory connectionFactory) {
        // RedisTemplate 인스턴스 생성
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // Redis 연결 팩토리 설정
        redisTemplate.setConnectionFactory(connectionFactory);
        // 키 직렬화기 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 값 직렬화기 설정
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    // 채널 주제 설정
    @Bean
    public ChannelTopic channelTopic() {
        // "chatroom"이라는 이름의 ChannelTopic 인스턴스 생성
        return new ChannelTopic("chatroom");
    }
}

