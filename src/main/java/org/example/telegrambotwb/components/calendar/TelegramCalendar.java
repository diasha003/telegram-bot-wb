package org.example.telegrambotwb.components.calendar;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.time.*;
import java.util.*;

@Component
public class TelegramCalendar {

    public static final String IGNORE_PREFIX = "cal_ignore;";
    public static final String PREV_PREFIX = "cal_prev;";
    public static final String NEXT_PREFIX = "cal_next;";
    public static final String CANCEL_PREFIX = "cal_cancel;";
    public static final String DAY_PREFIX = "cal_day;";
    public static final String TOGGLE_WEEKS_PREFIX = "cal_toggle_weeks;";


    public InlineKeyboardMarkup createCalendar(int year, int month, boolean showWeekList) {

        //periods of weeks
        InlineKeyboardButton periodsButton = InlineKeyboardButton.builder().text("Выбрать неделю:").callbackData(TOGGLE_WEEKS_PREFIX).build();

        //header calendar
        List<InlineKeyboardButton> headerRow = List.of(
                InlineKeyboardButton.builder().text("prev").callbackData(PREV_PREFIX).build(),
                InlineKeyboardButton.builder().text("current").callbackData(IGNORE_PREFIX).build(),
                InlineKeyboardButton.builder().text("next").callbackData(NEXT_PREFIX).build()
        );

        //footer calendar
        List<InlineKeyboardButton> footerRow = List.of(
                InlineKeyboardButton.builder().text("cancel").callbackData(CANCEL_PREFIX).build()
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
