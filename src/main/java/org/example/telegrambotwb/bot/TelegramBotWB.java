package org.example.telegrambotwb.bot;


import org.example.telegrambotwb.calendar.TelegramCalendar;
import org.example.telegrambotwb.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@Component
public class TelegramBotWB extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    public TelegramBotWB(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCommand(chatId);
                    System.out.println("/start");
                    break;
                default:
                    System.out.println("unexpected message");
            }


        }

    }

    private void startCommand(long chatId){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hello!!");

        try {
            execute(sendMessage);
            System.out.println("Reply sent");
        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }



    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }


    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
