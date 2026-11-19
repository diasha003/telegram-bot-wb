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
    private short year;

    @JsonProperty("m")
    private byte month;

    @JsonProperty("s")
    private byte showWeekList;

    public CalendarCallbackDto(String message){
        receivedMessage = message;
    }

}
