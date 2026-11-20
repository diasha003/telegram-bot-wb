package org.example.telegrambotwb.dto.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class CalendarWeekPeriodCallbackDto implements Serializable {
    @JsonProperty("dF")
    public LocalDate dateFrom;

    @JsonProperty("dT")
    public LocalDate dateTo;

    public String toBase64() {
        ByteBuffer buffer = ByteBuffer.allocate(3 * 2 * Integer.BYTES);

        buffer.putInt(dateFrom.getYear());
        buffer.putInt(dateFrom.getMonthValue());
        buffer.putInt(dateFrom.getDayOfMonth());

        buffer.putInt(dateTo.getYear());
        buffer.putInt(dateTo.getMonthValue());
        buffer.putInt(dateTo.getDayOfMonth());

        return new String(Base64.encodeBase64(buffer.array()));
    }

    public static CalendarWeekPeriodCallbackDto fromBase64(String base64) {
        byte[] bytes = Base64.decodeBase64(base64);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        List<Integer> numbers = new ArrayList<>();
        while (buffer.remaining() >= Integer.BYTES) {
            numbers.add(buffer.getInt());
        }

        int dateFromYear = numbers.get(0);
        int dateFromMonth = numbers.get(1);
        int dateFromDay = numbers.get(2);

        int dateToYear = numbers.get(3);
        int dateToMonth = numbers.get(4);
        int dateToDay = numbers.get(5);

        return new CalendarWeekPeriodCallbackDto(
                LocalDate.of(dateFromYear, dateFromMonth, dateFromDay),
                LocalDate.of(dateToYear, dateToMonth, dateToDay));
    }
}
