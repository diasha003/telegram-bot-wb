package org.example.telegrambotwb.dto.api;

import lombok.Data;

import java.util.List;

@Data
public class ListReportFileResponseDto {

    public List<ReportFileResponseDto> listReport;
}
