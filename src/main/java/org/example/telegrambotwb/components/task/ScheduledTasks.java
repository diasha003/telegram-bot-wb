package org.example.telegrambotwb.components.task;

import org.example.telegrambotwb.service.report.ReportsService;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final ReportsService reportsService;

    public ScheduledTasks(ReportsService reportsService) {
        this.reportsService = reportsService;
    }
}
