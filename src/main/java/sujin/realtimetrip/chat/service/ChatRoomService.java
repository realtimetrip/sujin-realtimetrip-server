package sujin.realtimetrip.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sujin.realtimetrip.chat.dto.ChatRoomDto;
import sujin.realtimetrip.chat.dto.CountryDto;
import sujin.realtimetrip.chat.entity.ChatRoom;
import sujin.realtimetrip.chat.repository.ChatRoomRepository;
import sujin.realtimetrip.country.entity.Country;
import sujin.realtimetrip.country.repository.CountryRepository;
import sujin.realtimetrip.global.exception.CustomException;
import sujin.realtimetrip.global.exception.ErrorCode;

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
                        room.getId().toString(),
                        room.getCountry().getCountryName(),
                        room.getUserCount()))
                .collect(Collectors.toList());
    }
}