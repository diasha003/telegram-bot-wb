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

}
