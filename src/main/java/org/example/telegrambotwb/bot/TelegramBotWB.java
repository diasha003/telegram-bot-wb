package org.example.telegrambotwb.bot;

import org.example.telegrambotwb.components.BotCommands;
import org.example.telegrambotwb.components.calendar.TelegramCalendar;
import org.example.telegrambotwb.config.bot.BotConfig;
import org.example.telegrambotwb.dto.callback.CalendarCallbackDto;
import org.example.telegrambotwb.dto.callback.CalendarWeekPeriodCallbackDto;
import org.example.telegrambotwb.reports.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import tools.jackson.databind.json.JsonMapper;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.YearMonth;

@Component
public class TelegramBotWB extends TelegramLongPollingBot implements BotCommands {

    private final BotConfig botConfig;
    private final TelegramCalendar telegramCalendar;
    private final JsonMapper jsonMapper;
    private final ReportsService reportsService;

    private short year;
    private byte month;


    public TelegramBotWB(BotConfig botConfig, TelegramCalendar telegramCalendar, JsonMapper jsonMapper, ReportsService reportsService) {
        this.reportsService = reportsService;

        LocalDate now = LocalDate.now();
        year = (short) now.getYear();
        month = (byte) now.getMonth().getValue();

        this.botConfig = botConfig;
        this.telegramCalendar = telegramCalendar;
        this.jsonMapper = jsonMapper;

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId;
        String messageText;

        if(update.hasMessage()){

            chatId = update.getMessage().getChatId();

            if(update.getMessage().hasText()){
                messageText = update.getMessage().getText();
                handleCommand(messageText, chatId);
            }

        } else if (update.hasCallbackQuery()){

            handleCallbackQuery(update.getCallbackQuery());

        }
    }



    private void handleCommand(String receivedMessage, long chatId){

        switch (receivedMessage){
            case "/start":
                startCommand(chatId);
                break;
            case "/weeklyreports":
                weeklyReportCommand(chatId);
                break;
            case "/dailyreports":
                dailyReportCommand(chatId);
                break;
            default:
                System.out.println("unexpected message");
        }

    }

    private void handleCallbackQuery(CallbackQuery callbackQuery){

        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        String callbackDataStr = callbackQuery.getData();
        if (callbackDataStr.startsWith(WEEK_PREFIX)) {
            String[] parts = callbackDataStr.split("_");
            String payload = parts[parts.length - 1];
            CalendarWeekPeriodCallbackDto dto = CalendarWeekPeriodCallbackDto.fromBase64(payload);
            ResponseEntity<byte[]> fileData = reportsService.getWeeklyReport(dto);
            sendBytesAsFile(chatId, fileData.getBody(), fileData.getHeaders().getContentDisposition().getFilename());
            return;
        }

        CalendarCallbackDto callbackData = jsonMapper.readValue(callbackQuery.getData(), CalendarCallbackDto.class);
        String messageText = callbackData.getReceivedMessage();


        YearMonth targetData;
        switch (messageText){
            case TOGGLE_WEEKS_PREFIX:
                editWeeksCalendar(chatId, messageId, callbackData.getYear(), callbackData.getMonth(), (byte) (callbackData.getShowWeekList() == 1 ? 0: 1));
                break;
            case TOGGLE_DAYS_PREFIX:
                editDaysCalendar(chatId, messageId, callbackData.getYear(), callbackData.getMonth(), (byte) (callbackData.getShowWeekList() == 1 ? 0: 1));
                break;
            case PREV_PREFIX_WEEKS:
                targetData = YearMonth.of(callbackData.getYear(), callbackData.getMonth()).minusMonths(1);
                editWeeksCalendar(chatId, messageId, targetData.getYear() , targetData.getMonth().getValue(), callbackData.getShowWeekList());
                break;
            case PREV_PREFIX_DAYS:
                targetData = YearMonth.of(callbackData.getYear(), callbackData.getMonth()).minusMonths(1);
                editDaysCalendar(chatId, messageId, targetData.getYear() , targetData.getMonth().getValue(), callbackData.getShowWeekList());
                break;
            case NEXT_PREFIX_WEEKS:
                targetData = YearMonth.of(callbackData.getYear(), callbackData.getMonth()).plusMonths(1);
                editWeeksCalendar(chatId, messageId, targetData.getYear() , targetData.getMonth().getValue(), callbackData.getShowWeekList());
                break;
            case NEXT_PREFIX_DAYS:
                targetData = YearMonth.of(callbackData.getYear(), callbackData.getMonth()).plusMonths(1);
                editDaysCalendar(chatId, messageId, targetData.getYear() , targetData.getMonth().getValue(), callbackData.getShowWeekList());
                break;
            case IGNORE_PREFIX:
                return;
            case CANCEL_PREFIX_WEEKS:
                editWeeksCalendar(chatId, messageId, this.year , this.month, callbackData.getShowWeekList());
                break;
            case CANCEL_PREFIX_DAYS:
                editDaysCalendar(chatId, messageId, this.year , this.month, callbackData.getShowWeekList());
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
                        
                        Выберите необходимую команду:
                        
                        /weeklyreports — Еженедельные отчеты
                        /dailyreports — Ежедневные отчеты
                        /subscriptionweekly — Подписка на еженедельные отчеты
                        /subscriptiondaily — Подписка на ежедневные отчеты""";

        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }


    private void weeklyReportCommand(long chatId){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String text = """
                Выберите неделю для построения отчета
                
                """;


        sendMessage.setText(text);
        sendMessage.setReplyMarkup(telegramCalendar.createWeeksCalendar(year, month, (byte) 0));

        try {
            execute(sendMessage);
            System.out.println("Reply sent");
        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }

    private void dailyReportCommand(long chatId){

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        String text = """
                Выберите день для построения отчета
                
                """;


        sendMessage.setText(text);
        sendMessage.setReplyMarkup(telegramCalendar.createDaysCalendar(year, month, (byte) 0));

        try {
            execute(sendMessage);
        } catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }

    }


    private void editWeeksCalendar(Long chatId, Integer messageId, int yearNew, int monthNew, byte nextShowWeekListState ){

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(telegramCalendar.createWeeksCalendar((short) yearNew, (byte) monthNew, nextShowWeekListState));

        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }

    }

    private void editDaysCalendar(Long chatId, Integer messageId, int yearNew, int monthNew, byte nextShowWeekListState ){

        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId);
        editMessageReplyMarkup.setMessageId(messageId);
        editMessageReplyMarkup.setReplyMarkup(telegramCalendar.createDaysCalendar((short) yearNew, (byte) monthNew, nextShowWeekListState));

        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
        }

    }

    private void sendBytesAsFile(Long chatId, byte[] bytes, String fileName){

        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        InputFile inputFile = new InputFile(inputStream, fileName);

        SendDocument sendDocument = SendDocument.builder()
                .chatId(chatId)
                .document(inputFile)
                .build();

        try {
            execute(sendDocument);
        } catch (TelegramApiException e){
            System.out.println(e.getMessage());
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
