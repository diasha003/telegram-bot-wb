package org.example.telegrambotwb.dto.callback;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


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

    @JsonProperty("dF")
    public LocalDate dateFrom;

    @JsonProperty("dT")
    public LocalDate dateTo;

    public CalendarCallbackDto(String message){

        this.receivedMessage = message;
    }

    public CalendarCallbackDto(String message, short year, byte month, byte showWeekList){

        this.receivedMessage = message;
        this.year = year;
        this.month = month;
        this.showWeekList = showWeekList;

    }

    public CalendarCallbackDto(String message, LocalDate dateFrom, LocalDate dateTo){

        this.receivedMessage = message;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;

    }

}
