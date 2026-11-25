package org.example.telegrambotwb.handlers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodSerializable;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class BaseCallbackHandler {
    public abstract void handle(TelegramLongPollingBot bot, CallbackQuery callbackQuery);

    protected void execute(TelegramLongPollingBot bot, BotApiMethodSerializable method) {
        try {
            bot.execute(method);
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }
    }
}
