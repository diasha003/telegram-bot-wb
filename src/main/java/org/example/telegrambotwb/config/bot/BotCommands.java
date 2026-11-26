package org.example.telegrambotwb.config.bot;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import java.util.*;

public final class BotCommands {

    public static final List<BotCommand> LIST_OF_COMMAND = List.of(
            new BotCommand("/start", "start bot"),
            new BotCommand("/weeklyreports", "weekly"),
            new BotCommand("/dailyreports", "daily"),
            new BotCommand("/subcriptionweekly", "subscription to receive weekly reports"),
            new BotCommand("/subcriptiondaily", "subscription to receive daily reports")
    );

    public static final String IGNORE_PREFIX = "ignore";
    public static final String PREV_PREFIX_WEEKS = "prev_w";
    public static final String PREV_PREFIX_DAYS = "prev_d";
    public static final String NEXT_PREFIX_WEEKS = "next_w";
    public static final String NEXT_PREFIX_DAYS = "next_d";
    public static final String CANCEL_PREFIX_WEEKS = "cancel_w";
    public static final String CANCEL_PREFIX_DAYS = "cancel_d";
    public static final String DAY_PREFIX = "day";
    public static final String WEEK_PREFIX = "week";
    public static final String TOGGLE_WEEKS_PREFIX = "t_weeks";
    public static final String TOGGLE_DAYS_PREFIX = "t_days";

}
