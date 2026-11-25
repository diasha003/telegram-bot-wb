package org.example.telegrambotwb.components.calendar;

import org.example.telegrambotwb.components.BotCommands;
import org.example.telegrambotwb.dto.callback.CalendarDataCallbackDto;
import org.example.telegrambotwb.dto.callback.WeeksCalendarCallbackDto;
import org.example.telegrambotwb.dto.callback.CalendarWeekPeriodCallbackDto;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

import static org.example.telegrambotwb.components.BotCommands.*;

@Component
public class TelegramCalendar {
    private static List<InlineKeyboardButton> createNavigationMenuCalendar(short year, byte month, byte showWeekList, String types){
        JsonMapper jsonMapper = new JsonMapper();

        WeeksCalendarCallbackDto calendarCallbackPrevDto = new WeeksCalendarCallbackDto("weeks".equals(types) ? PREV_PREFIX_WEEKS : PREV_PREFIX_DAYS, year, month,showWeekList);
        String resultPrev = jsonMapper.writeValueAsString(calendarCallbackPrevDto);

        WeeksCalendarCallbackDto calendarCallbackNextDto = new WeeksCalendarCallbackDto("weeks".equals(types) ? NEXT_PREFIX_WEEKS : NEXT_PREFIX_DAYS, year, month,showWeekList);
        String resultNext = jsonMapper.writeValueAsString(calendarCallbackNextDto);

        WeeksCalendarCallbackDto calendarCallbackIgnoreDto = new WeeksCalendarCallbackDto(IGNORE_PREFIX);
        String resultIgnore = jsonMapper.writeValueAsString(calendarCallbackIgnoreDto);


        return List.of(
                InlineKeyboardButton.builder().text("⬅️").callbackData(resultPrev).build(),
                InlineKeyboardButton.builder().text(YearMonth.of(year,month).getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + YearMonth.of(year,month).getYear()).callbackData(resultIgnore).build(),
                InlineKeyboardButton.builder().text("➡️").callbackData(resultNext).build()
        );

    }

    private static InlineKeyboardButton createFooterCalendar(short year, byte month, byte showWeekList, String types){
        JsonMapper jsonMapper = new JsonMapper();

        WeeksCalendarCallbackDto calendarCallbackCancelDto = new WeeksCalendarCallbackDto("weeks".equals(types) ? CANCEL_PREFIX_WEEKS : CANCEL_PREFIX_DAYS, year, month,showWeekList);
        String resultCancel = jsonMapper.writeValueAsString(calendarCallbackCancelDto);

        return InlineKeyboardButton.builder().text("❌").callbackData(resultCancel).build();

    }

    private static InlineKeyboardButton createHeaderCalendar(String resultPeriod, String dropDownText){

        return InlineKeyboardButton.builder().text(dropDownText).callbackData(resultPeriod).build();

    }

    public static InlineKeyboardMarkup createWeeksCalendar(short year, byte month, byte showWeekList) {
        JsonMapper jsonMapper = new JsonMapper();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        WeeksCalendarCallbackDto calendarCallbackPeriodDto = new WeeksCalendarCallbackDto(TOGGLE_WEEKS_PREFIX, year, month, showWeekList);
        String resultPeriod = jsonMapper.writeValueAsString(calendarCallbackPeriodDto);
        String dropDownText = showWeekList == 1 ? "\uD83D\uDD3D Выберите неделю ниже:" : "Выбрать неделю:";
        buttons.add(List.of(createHeaderCalendar(resultPeriod, dropDownText)));

        //list of weeks
        int countDaysOfMonth = YearMonth.of(year,month).lengthOfMonth();
        double countWeeksOfMonth = (int) Math.ceil(countDaysOfMonth/7.0);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate firstDayOfWeek = LocalDate.of(year, month, 1);
        LocalDate startDayOfWeekOfMonth = firstDayOfWeek.with(DayOfWeek.MONDAY);
        Calendar calendar = Calendar.getInstance();

        if ( showWeekList == 1 ) {

            for (int i=0; i < countWeeksOfMonth ; i++){
                LocalDate startDayOfWeek = startDayOfWeekOfMonth.plusWeeks(i);
                LocalDate endDayOfWeek = startDayOfWeek.plusDays(6);

                Instant instant = Instant.from(startDayOfWeek.atStartOfDay(ZoneId.of("GMT")));
                calendar.setTime(Date.from(instant));

                CalendarWeekPeriodCallbackDto calendarWeekPeriodCallbackDto = new CalendarWeekPeriodCallbackDto(startDayOfWeek, endDayOfWeek);
                String result = WEEK_PREFIX + "_" + calendarWeekPeriodCallbackDto.toBase64();

                String buttonText = calendar.get(Calendar.WEEK_OF_YEAR) + " неделя (" + startDayOfWeek.format(formatter) + " - " + endDayOfWeek.format(formatter) + ")";
                buttons.add(List.of(
                        InlineKeyboardButton.builder()
                                .text(buttonText)
                                .callbackData(result)
                                .build()));
            }

        }

        buttons.add(createNavigationMenuCalendar(year, month, showWeekList, "weeks"));
        buttons.add(List.of(createFooterCalendar(year, month, showWeekList, "weeks")));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(buttons);

        return markupInline;

    }

    public static InlineKeyboardMarkup createDaysCalendar(short year, byte month, byte showWeekList){
        JsonMapper jsonMapper = new JsonMapper();

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();

        WeeksCalendarCallbackDto calendarCallbackPeriodDto = new WeeksCalendarCallbackDto(TOGGLE_DAYS_PREFIX, year, month, showWeekList);
        String resultPeriod = jsonMapper.writeValueAsString(calendarCallbackPeriodDto);
        String dropDownText = showWeekList == 1 ? "\uD83D\uDD3D Выберите день ниже:" : "Выбрать день:";
        buttons.add(List.of(createHeaderCalendar(resultPeriod, dropDownText)));

        //list of days

        YearMonth yearMonth = YearMonth.of(year,month);
        int countDaysOfMonth = yearMonth.lengthOfMonth();
        int offset = yearMonth.atDay(1).getDayOfWeek().getValue()-1;

        int totalCells = offset + countDaysOfMonth;
        int countWeeksOfMonth = (int) Math.ceil(totalCells/7.0);

        int day = 1;

        if (showWeekList == 1){

            List<String> weekDayLabels = List.of("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс");
            List<InlineKeyboardButton> headerRow = new ArrayList<>();
            for (String label : weekDayLabels) {
                headerRow.add(
                        InlineKeyboardButton.builder()
                                .text(label)
                                .callbackData("IGNORE")
                                .build()
                );
            }
            buttons.add(headerRow);


            for(int weekNumber=0; weekNumber <countWeeksOfMonth; weekNumber++ ){

                List<InlineKeyboardButton> daysWeekButton = new ArrayList<>();

                for(int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++){

                    int dayOfMonth = weekNumber*7 + dayOfWeek;

                    if(day > countDaysOfMonth || dayOfMonth < offset){

                        daysWeekButton.add(
                                InlineKeyboardButton.builder()
                                        .text(String.valueOf("  ")).
                                        callbackData(IGNORE_PREFIX)
                                        .build());

                    } else {

                        CalendarDataCallbackDto calendarDataCallbackDtoCallbackDto = new CalendarDataCallbackDto(LocalDate.of(year, month, day));
                        String result = DAY_PREFIX + "_" + calendarDataCallbackDtoCallbackDto.toBase64();

                        daysWeekButton.add(InlineKeyboardButton.builder().
                                text(String.valueOf(day))
                                .callbackData(result)
                                .build());
                        day++;

                    }

                }

                buttons.add(daysWeekButton);

            }

        }

        buttons.add(createNavigationMenuCalendar(year, month, showWeekList, "days"));
        buttons.add(List.of(createFooterCalendar(year, month, showWeekList, "days")));

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(buttons);

        return markupInline;


    }

}
