package org.example.telegrambotwb.bot;

import org.example.telegrambotwb.components.BotCommands;
import org.example.telegrambotwb.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class TelegramBotWB extends TelegramLongPollingBot implements BotCommands {

    private final BotConfig botConfig;


    public TelegramBotWB(BotConfig botConfig) {

        this.botConfig = botConfig;

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public void onUpdateReceived(Update update) {

        long chatId;
        String messageText;

        if(update.hasMessage()){

            chatId = update.getMessage().getChatId();

            if(update.getMessage().hasText()){
                messageText = update.getMessage().getText();
                botAnswerUtils(messageText, chatId);
            }

        } else if (update.hasCallbackQuery()){

            chatId = update.getCallbackQuery().getMessage().getChatId();
            messageText = update.getCallbackQuery().getData();

            botAnswerUtils(messageText, chatId);

        }
    }


    private void botAnswerUtils(String receivedMessage, long chatId){

        switch (receivedMessage){
            case "/start":
                startCommand(chatId);
                System.out.println("/start");
                break;
            default:
                System.out.println("unexpected message");
        }

    }


    private void startCommand(long chatId){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String text = """
                Добро пожаловать!
                
                Выберите неделю для построения отчета
                
                """;


        sendMessage.setText(text);

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
