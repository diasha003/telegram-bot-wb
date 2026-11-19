package org.example.telegrambotwb.dto.api;

import lombok.Data;

@Data
public class ReportIssueResponseDto {

    public String severity;
    public String code;
    public String message;
    public Long nmId;

}
