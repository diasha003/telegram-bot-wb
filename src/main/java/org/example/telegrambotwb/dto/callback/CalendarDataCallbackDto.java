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
public class CalendarDataCallbackDto implements Serializable {


    @JsonProperty("d")
    public LocalDate date;

    public String toBase64() {
        ByteBuffer buffer = ByteBuffer.allocate(3 * Integer.BYTES);

        buffer.putInt(date.getYear());
        buffer.putInt(date.getMonthValue());
        buffer.putInt(date.getDayOfMonth());

        return new String(Base64.encodeBase64(buffer.array()));
    }

    public static CalendarDataCallbackDto fromBase64(String base64) {
        byte[] bytes = Base64.decodeBase64(base64);
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        List<Integer> numbers = new ArrayList<>();

        while (buffer.remaining() >= Integer.BYTES) {
            numbers.add(buffer.getInt());
        }

        int dateYear = numbers.get(0);
        int dateMonth = numbers.get(1);
        int dateDay = numbers.get(2);


        return new CalendarDataCallbackDto(
                LocalDate.of(dateYear, dateMonth, dateDay));
    }

}
