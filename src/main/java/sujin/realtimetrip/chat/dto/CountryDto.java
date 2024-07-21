package sujin.realtimetrip.chat.dto;

import lombok.Data;

@Data
public class CountryDto {
    private String countryCode;
    private String countryName;
    private String continent;

    public CountryDto(String countryCode, String countryName, String continent) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.continent = continent;
    }
}
