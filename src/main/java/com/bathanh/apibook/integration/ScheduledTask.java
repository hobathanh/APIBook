package com.bathanh.apibook.domain.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final ItBookService itBookService;
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    @Scheduled(fixedRate = 600000)
    public void storeNewBooksPeriodically() {
        try {
            log.info("Start retrieving new books");
            itBookService.storeNewBooks();
            log.info("Retrieving new books done");
        } catch (Exception ex) {
            log.error("Failed to retrieve new books with error", ex);
        }
    }
}
