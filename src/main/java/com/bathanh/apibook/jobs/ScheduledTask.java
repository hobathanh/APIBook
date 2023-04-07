package com.bathanh.apibook.jobs;

import com.bathanh.apibook.domain.book.ItBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.util.concurrent.TimeUnit.HOURS;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final ItBookService itBookService;

    @PostConstruct
    public void init() throws SchedulerException {
        final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    @Scheduled(fixedRate = 6, timeUnit = HOURS)
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
