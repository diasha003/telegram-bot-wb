package org.example.telegrambotwb.config.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApiConfiguration {

    @Bean
    public RestClient responce(){

        return RestClient.builder().build();

    }
}
