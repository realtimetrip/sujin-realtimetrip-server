package sujin.realtimetrip.email.dto;

import lombok.Data;

@Data
public class EmailAuthDto {
    private String email;
    private String authCode;
}
