package org.example.telegrambotwb.components;

import jakarta.validation.constraints.Max;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface BotCommands {

  List<BotCommand> LIST_OF_COMMAND = List.of(
          new BotCommand("/start", "start bot"),
          new BotCommand("/weeklyreports", "weekly"),
          new BotCommand("/dailyreports", "daily"),
          new BotCommand("/subcriptionweekly", "subscription to receive weekly reports"),
          new BotCommand("/subcriptiondaily", "subscription to receive daily reports")
  );

  String IGNORE_PREFIX = "ignore";
  String PREV_PREFIX_WEEKS = "prev_w";
  String PREV_PREFIX_DAYS = "prev_d";
  String NEXT_PREFIX_WEEKS = "next_w";
  String NEXT_PREFIX_DAYS = "next_d";
  String CANCEL_PREFIX_WEEKS = "cancel_w";
  String CANCEL_PREFIX_DAYS = "cancel_d";
  String DAY_PREFIX = "day";
  String WEEK_PREFIX = "week";
  String TOGGLE_WEEKS_PREFIX = "t_weeks";
  String TOGGLE_DAYS_PREFIX = "t_days";

}
