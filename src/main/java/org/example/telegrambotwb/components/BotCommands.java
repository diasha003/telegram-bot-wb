package org.example.telegrambotwb.components;

import jakarta.validation.constraints.Max;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface BotCommands {

  List<BotCommand> LIST_OF_COMMAND = List.of(
          new BotCommand("/start", "start bot"),
          new BotCommand("/weeklyReports", ""),
          new BotCommand("/dailyReports", ""),
          new BotCommand("/subcriptionWeekly", "subscription to receive weekly reports"),
          new BotCommand("/subcriptionDaily", "subscription to receive daily reports")
  );

  String IGNORE_PREFIX = "ignore";
  String PREV_PREFIX = "prev";
  String NEXT_PREFIX = "next";
  String CANCEL_PREFIX = "cancel";
  String DAY_PREFIX = "day";
  String WEEK_PREFIX = "week";
  String TOGGLE_WEEKS_PREFIX = "t_weeks";

}
