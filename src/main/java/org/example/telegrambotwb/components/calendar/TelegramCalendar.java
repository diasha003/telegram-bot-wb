package org.example.telegrambotwb.components.calendar;

import org.example.telegrambotwb.components.BotCommands;
import org.example.telegrambotwb.dto.CalendarCallbackDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tools.jackson.databind.json.JsonMapper;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

@Component
public class TelegramCalendar implements BotCommands {

    private final JsonMapper jsonMapper;

    public TelegramCalendar(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public InlineKeyboardMarkup createCalendar(int year, int month, boolean showWeekList) {

        //periods of weeks
        InlineKeyboardButton periodsButton = InlineKeyboardButton.builder().text("Выбрать неделю:").callbackData(TOGGLE_WEEKS_PREFIX).build();

        //header calendar
        CalendarCallbackDto calendarCallbackPrevDto = new CalendarCallbackDto(PREV_PREFIX, year, month);
        String resultPrev = jsonMapper.writeValueAsString(calendarCallbackPrevDto);

        CalendarCallbackDto calendarCallbackNextDto = new CalendarCallbackDto(NEXT_PREFIX, year, month);
        String resultNext = jsonMapper.writeValueAsString(calendarCallbackNextDto);

        List<InlineKeyboardButton> headerRow = List.of(

                InlineKeyboardButton.builder().text("⬅️").callbackData(resultPrev).build(),
                InlineKeyboardButton.builder().text(YearMonth.of(year,month).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + YearMonth.of(year,month).getYear()).callbackData(IGNORE_PREFIX).build(),
                InlineKeyboardButton.builder().text("➡️").callbackData(resultNext).build()
        );

        //footer calendar
        List<InlineKeyboardButton> footerRow = List.of(
                InlineKeyboardButton.builder().text("❌").callbackData(CANCEL_PREFIX).build()
        );

        List<List<InlineKeyboardButton>> buttons = List.of(
                List.of(periodsButton),
                headerRow,
                footerRow
        );

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(buttons);

        return markupInline;

    }


}
