package sujin.realtimetrip.Mail.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailAuthDto {
    private int userId;
    private String email;
    private int authNum;
    private LocalDateTime createdAt;
}
