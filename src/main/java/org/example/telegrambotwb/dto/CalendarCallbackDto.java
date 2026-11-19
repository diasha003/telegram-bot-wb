package org.example.telegrambotwb.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalendarCallbackDto {

    @JsonProperty("p")
    private String receivedMessage;

    @JsonProperty("y")
    private Integer year;

    @JsonProperty("m")
    private  Integer month;


}
