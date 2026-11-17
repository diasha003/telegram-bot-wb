package org.example.telegrambotwb.config;

import org.example.telegrambotwb.bot.TelegramBotWB;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {

    private final TelegramBotWB telegramBotWB;

    public BotInitializer(TelegramBotWB telegramBotWB) {
        this.telegramBotWB = telegramBotWB;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init(){

        try {

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot((LongPollingBot) telegramBotWB);

        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }
}
