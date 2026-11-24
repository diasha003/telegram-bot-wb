package org.example.telegrambotwb.dto.callback;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class WeeksCalendarCallbackDto {

    @JsonProperty("p")
    private String receivedMessage;

    @JsonProperty("y")
    private short year;

    @JsonProperty("m")
    private byte month;

    @JsonProperty("s")
    private byte showWeekList;


    public WeeksCalendarCallbackDto(String message){

        this.receivedMessage = message;
    }

}
