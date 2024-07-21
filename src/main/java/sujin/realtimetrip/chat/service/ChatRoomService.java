package sujin.realtimetrip.chat.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.chat.dto.*;
import sujin.realtimetrip.chat.entity.ChatRoom;
import sujin.realtimetrip.chat.entity.ChatRoomUser;
import sujin.realtimetrip.chat.enums.MessageType;
import sujin.realtimetrip.chat.repository.ChatRoomRepository;
import sujin.realtimetrip.chat.repository.ChatRoomUserRepository;
import sujin.realtimetrip.country.entity.Country;
import sujin.realtimetrip.country.repository.CountryRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final CountryRepository countryRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public List<CountryDto> insertCountriesAndChatRooms() {
        String[] countryData = {
                "KR,대한민국,Asia",
                "JP,일본,Asia",
                "GB,영국,Europe",
                "ES,스페인,Europe",
                "US,미국,N.America",
                "CA,캐나다,N.America",
                "BR,브라질,S.America",
                "AR,아르헨티나,S.America",
                "AU,호주,Oceania",
                "NZ,뉴질랜드,Oceania",
                "ZA,남아프리카공화국,Africa",
                "EG,이집트,Africa"
        };

        List<CountryDto> countries = new ArrayList<>();
        for (String data : countryData) {
            String[] parts = data.split(",");
            String countryCode = parts[0];
            String countryName = parts[1];
            String continent = parts[2];

            // 중복 체크
            Optional<Country> existingCountry = countryRepository.findByCountryCode(countryCode);
            if (existingCountry.isPresent()) {
                throw new CustomException(ErrorCode.COUNTRY_CODE_DUPLICATED, "countryCode: " + countryCode);
            }

            // country 생성
            Country country = new Country(countryCode, countryName, continent);
            country = countryRepository.save(country);


            // 채팅방 생성
            ChatRoom chatRoom = new ChatRoom(country);
            chatRoomRepository.save(chatRoom);

            countries.add(new CountryDto(countryCode, countryName, continent));
        }

        return countries;
    }

    public List<ChatRoomDto> getChatRoom() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                .map(room -> new ChatRoomDto(
                        room.getId(),
                        room.getCountry().getCountryName(),
                        room.getUserCount()))
                .collect(Collectors.toList());
    }

    public List<ChatRoomUserDto> getChatRoomUsers(String chatRoomId) {
        List<ChatRoomUser> chatRoomUsers = chatRoomUserRepository.findByChatRoomId(chatRoomId);
        return chatRoomUsers.stream()
                .map(user -> new ChatRoomUserDto(
                        user.getUser().getId().toString(),
                        user.getUser().getNickName(),
                        user.getUser().getProfile()))
                .collect(Collectors.toList());
    }

    public ChatRoomMessagesDto getChatRoomMessages(String chatRoomId, int size, Long chatId) {
        // 채팅방 ID에 해당하는 나라 이름 데이터베이스에서 조회
        String countryName = fetchCountryNameByChatRoomId(chatRoomId);

        // Redis에서 메시지를 가져오기 위한 키 생성
        String key = "chatRoom:" + chatRoomId + ":messages";
        // chatId가 주어진 경우, 해당 ID 이전의 메시지를 조회
        long end = (chatId != null && chatId > 0) ? chatId - 1 : -1;
        // 요청한 size만큼의 메시지를 조회하기 위해 시작 인덱스 계산
        long start = (end >= size) ? end - size + 1 : 0;

        // Redis에서 범위에 해당하는 메시지 리스트를 조회
        List<Object> messagesJson = redisTemplate.opsForList().range(key, start, end);
        assert messagesJson != null;
        // JSON 문자열을 ChatResponse 객체로 변환
        List<ChatResponse> messages = messagesJson.stream()
                .map(json -> convertJsonToChatResponse((String) json))
                .collect(Collectors.toList());

        return new ChatRoomMessagesDto(chatRoomId, countryName, messages);
    }

    private String fetchCountryNameByChatRoomId(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(ErrorCode.COUNTRY_NOT_FOUND));
        Country country = countryRepository.findById(chatRoom.getCountry().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.COUNTRY_NOT_FOUND));
        return country.getCountryName();
    }

    private ChatResponse convertJsonToChatResponse(String json) {
        try {
            // JSON 문자열에서 필요한 데이터 추출
            JsonNode node = objectMapper.readTree(json);
            Long chatId = node.get("chatId").asLong();
            String chatRoomId = node.get("chatRoomId").asText();
            Long userId = node.get("userId").asLong();
            String nickName = node.get("nickName").asText();
            String message = node.get("message").asText();
            LocalDateTime eventTime = LocalDateTime.parse(node.get("eventTime").asText());
            MessageType type = MessageType.valueOf(node.get("type").asText());

            // 새로운 생성자를 사용하여 객체 생성
            return new ChatResponse(chatId, chatRoomId, userId, nickName, message, eventTime, type);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON to ChatResponse: " + json, e);
        }
    }

}