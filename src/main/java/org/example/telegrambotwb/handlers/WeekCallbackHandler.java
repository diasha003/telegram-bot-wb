package org.example.telegrambotwb.handlers;

import org.example.telegrambotwb.components.calendar.TelegramCalendar;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class WeekCallbackHandler extends BaseCallbackHandler{

    protected void editWeeksCalendar(TelegramLongPollingBot bot, Long chatId, Integer messageId, int yearNew, int monthNew, byte nextShowWeekListState ){

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(TelegramCalendar.createWeeksCalendar((short) yearNew, (byte) monthNew, nextShowWeekListState));

        try {
            bot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }

    }
}
