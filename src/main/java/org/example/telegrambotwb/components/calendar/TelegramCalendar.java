package org.example.telegrambotwb.components.calendar;

import org.example.telegrambotwb.components.BotCommands;
import org.example.telegrambotwb.dto.CalendarCallbackDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tools.jackson.databind.json.JsonMapper;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Component
public class TelegramCalendar implements BotCommands {

    private final JsonMapper jsonMapper;


    public TelegramCalendar(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;

    }

    public InlineKeyboardMarkup createCalendar(short year, byte month, byte showWeekList) {


        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        //periods of weeks
        CalendarCallbackDto calendarCallbackPeriodDto = new CalendarCallbackDto(TOGGLE_WEEKS_PREFIX, year, month, showWeekList);
        String resultPeriod = jsonMapper.writeValueAsString(calendarCallbackPeriodDto);

        String dropDownText = showWeekList == 1 ? "\uD83D\uDD3D Выберите неделю ниже:" : "Выбрать неделю:";
        InlineKeyboardButton periodsButton = InlineKeyboardButton.builder().text(dropDownText).callbackData(resultPeriod).build();
        buttons.add(List.of(periodsButton));

        //list of weeks
        int countDaysOfMonth = YearMonth.of(year,month).lengthOfMonth();
        double countWeeksOfMonth = (int) Math.ceil(countDaysOfMonth/7.0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate firstDayOfWeek = LocalDate.of(year, month, 1);
        LocalDate startDayOfWeekOfMonth = firstDayOfWeek.with(DayOfWeek.MONDAY);

        List<Map<String,String>>  listOfWeeks = new ArrayList<>();

        for (int i=0; i < countWeeksOfMonth ; i++){

            LocalDate startDayOfWeek = startDayOfWeekOfMonth.plusWeeks(i);
            LocalDate endDayOfWeek = startDayOfWeek.plusDays(6);

            Calendar calendar = Calendar.getInstance();
            Instant instant = Instant.from(startDayOfWeek.atStartOfDay(ZoneId.of("GMT")));
            calendar.setTime(Date.from(instant));

            Map<String, String> week = new HashMap<>();
            week.put(calendar.get(Calendar.WEEK_OF_YEAR) + " неделя (" + startDayOfWeek.format(formatter) + " - " + endDayOfWeek.format(formatter) + ")", startDayOfWeek.toString() + "_" + endDayOfWeek.toString());
            listOfWeeks.add(week);
        }

        if (showWeekList == 1){

            for(Map<String, String> week: listOfWeeks){

                String weekKey = week.keySet().toArray()[0].toString();
                buttons.add(List.of(InlineKeyboardButton.builder().text(weekKey).callbackData(IGNORE_PREFIX).build()));

            }
        }


        //header calendar
        CalendarCallbackDto calendarCallbackPrevDto = new CalendarCallbackDto(PREV_PREFIX, year, month,showWeekList);
        String resultPrev = jsonMapper.writeValueAsString(calendarCallbackPrevDto);

        CalendarCallbackDto calendarCallbackNextDto = new CalendarCallbackDto(NEXT_PREFIX, year, month,showWeekList);
        String resultNext = jsonMapper.writeValueAsString(calendarCallbackNextDto);

        CalendarCallbackDto calendarCallbackIgnoreDto = new CalendarCallbackDto(IGNORE_PREFIX);
        String resultIgnore = jsonMapper.writeValueAsString(calendarCallbackIgnoreDto);


        List<InlineKeyboardButton> headerRow = List.of(
                InlineKeyboardButton.builder().text("⬅️").callbackData(resultPrev).build(),
                InlineKeyboardButton.builder().text(YearMonth.of(year,month).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + YearMonth.of(year,month).getYear()).callbackData(resultIgnore).build(),
                InlineKeyboardButton.builder().text("➡️").callbackData(resultNext).build()
        );

        buttons.add(headerRow);


        //footer calendar
        CalendarCallbackDto calendarCallbackCancelDto = new CalendarCallbackDto(CANCEL_PREFIX, year, month,showWeekList);
        String resultCancel = jsonMapper.writeValueAsString(calendarCallbackCancelDto);

        InlineKeyboardButton footerRow = InlineKeyboardButton.builder().text("❌").callbackData(resultCancel).build();
        buttons.add(List.of(footerRow));


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(buttons);

        return markupInline;

    }


}
