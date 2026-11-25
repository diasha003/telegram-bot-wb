package org.example.telegrambotwb.handlers;

import lombok.AllArgsConstructor;
import org.example.telegrambotwb.components.BotCommands;
import org.example.telegrambotwb.dto.callback.WeeksCalendarCallbackDto;
import org.example.telegrambotwb.handlers.base.CallbackMethodAnnotation;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import tools.jackson.databind.json.JsonMapper;

import java.time.YearMonth;

@AllArgsConstructor
@CallbackMethodAnnotation(BotCommands.NEXT_PREFIX_WEEKS)
public class NextWeekCallbackHandler extends WeekCallbackHandler {
    @Override
    public void handle(TelegramLongPollingBot bot, CallbackQuery callbackQuery) {
        JsonMapper jsonMapper = new JsonMapper();
        WeeksCalendarCallbackDto callbackData = jsonMapper.readValue(callbackQuery.getData(), WeeksCalendarCallbackDto.class);
        YearMonth targetDate = YearMonth.of(callbackData.getYear(), callbackData.getMonth()).plusMonths(1);

        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        editWeeksCalendar(bot, chatId, messageId, targetDate.getYear(), (byte) targetDate.getMonth().getValue(), callbackData.getShowWeekList());

    }
}
