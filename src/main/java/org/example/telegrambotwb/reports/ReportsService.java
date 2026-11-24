package org.example.telegrambotwb.reports;

import org.example.telegrambotwb.dto.api.ReportFileResponseDto;
import org.example.telegrambotwb.dto.callback.CalendarDataCallbackDto;
import org.example.telegrambotwb.dto.callback.CalendarWeekPeriodCallbackDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReportsService {

    private final RestClient restClient;

    @Value("${x.seller}")
    private String xSeller;

    @Value("${api.base.url}")
    private String baseUrl;

    @Value("${x.api.key}")
    private String xApiKey;

    public ReportsService(RestClient restClient) {
        this.restClient = restClient;
    }


    public ResponseEntity<byte[]> getDailyReport(CalendarDataCallbackDto dto){

        List<ReportFileResponseDto> allReport = getAllReports();

        Optional<ReportFileResponseDto> resultDaily = allReport.stream().
                filter(item -> "Daily".equals(item.getPeriod()))
                .filter(item -> Objects.equals(item.getDateFrom(), dto.getDate()))
                .filter(item -> Objects.equals(item.getDateTo(), dto.getDate()))
                .findFirst();

        UUID idReport = resultDaily
                .map(ReportFileResponseDto::getId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return getFileReport(idReport);

    }

    public ResponseEntity<byte[]> getWeeklyReport(CalendarWeekPeriodCallbackDto dto){

        List<ReportFileResponseDto> allReport = getAllReports();

        Optional<ReportFileResponseDto> resultWeekly = allReport.stream().
                filter(item -> "Weekly".equals(item.getPeriod()))
                .filter(item -> Objects.equals(item.getDateFrom(), dto.getDateFrom()))
                .filter(item -> Objects.equals(item.getDateTo(), dto.getDateTo()))
                .findFirst();


        UUID idReport = resultWeekly
                .map(ReportFileResponseDto::getId)
                .orElseThrow(() -> new RuntimeException("Not found"));



        return getFileReport(idReport);

    }

    public  List<ReportFileResponseDto> getAllReports(){

        return restClient.get()
                .uri(baseUrl + "/api/Report/all")
                .header("X-Seller", xSeller)
                .header("X-Api-Key", xApiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

    }

    public ResponseEntity<byte[]> getFileReport(UUID idReport){

        return restClient.get()
                        .uri(baseUrl + "/api/Report" + "?tempFileId=" + idReport)
                        .header("X-Api-Key", xApiKey)
                        .retrieve()
                        .toEntity(byte[].class);

    }
}
