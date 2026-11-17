package org.example.telegrambotwb.calendar;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Component
public class TelegramCalendar {

    public static final String IGNORE_PREFIX = "cal_ignore;";
    public static final String PREV_PREFIX = "cal_prev;";
    public static final String NEXT_PREFIX = "cal_next;";
    public static final String CANCEL_PREFIX = "cal_cancel;";
    public static final String DAY_PREFIX = "cal_day;";
    public static final String TOGGLE_WEEKS_PREFIX = "cal_toggle_weeks;";


}
