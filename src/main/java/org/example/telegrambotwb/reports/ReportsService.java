package org.example.telegrambotwb.reports;

import org.example.telegrambotwb.dto.api.ReportFileResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

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

    public void getAllReports(){

        List<ReportFileResponseDto> result = restClient.get()
                .uri(baseUrl + "/api/Report/all")
                .header("X-Seller", xSeller)
                .header("X-Api-Key", xApiKey)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});


        System.out.println(result);

    }

}
