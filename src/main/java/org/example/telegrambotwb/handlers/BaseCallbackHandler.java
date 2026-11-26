package org.example.telegrambotwb.handlers;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tools.jackson.databind.json.JsonMapper;

public abstract class BaseCallbackHandler {

    protected final JsonMapper jsonMapper;

    protected BaseCallbackHandler(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public abstract void handle(TelegramLongPollingBot bot, CallbackQuery callbackQuery);

    protected void answerCallback(TelegramLongPollingBot bot, String id) {
        try {
            bot.execute(new AnswerCallbackQuery(id));
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

}
