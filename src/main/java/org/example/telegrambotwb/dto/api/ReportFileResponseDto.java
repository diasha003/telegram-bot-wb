package org.example.telegrambotwb.dto.api;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ReportFileResponseDto {

    public UUID id;
    public Instant timestamp;
    public String name;
    public String contentType;
    public String period;
    public LocalDate dateFrom;
    public LocalDate dateTo;
    public Boolean isRecalculating;
    public List<Integer> updDocumentIds;
    public List<ReportIssueResponseDto> issues;
}
