package sujin.realtimetrip.Mail.Dto;

import lombok.Data;

@Data
public class EmailAuthDto {
    private String email;
    private String authCode;
}
