package org.example.telegrambotwb.components;

import jakarta.validation.constraints.Max;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public interface BotCommands {

  List<BotCommand> LIST_OF_COMMAND = List.of(
          new BotCommand("/start", "start bot"),
          new BotCommand("/help", "bot info")
  );

  String IGNORE_PREFIX = "cal_ignore";
  String PREV_PREFIX = "cal_prev";
  String NEXT_PREFIX = "cal_next";
  String CANCEL_PREFIX = "cal_cancel";
  String DAY_PREFIX = "cal_day";
  String TOGGLE_WEEKS_PREFIX = "cal_toggle_weeks";

}
