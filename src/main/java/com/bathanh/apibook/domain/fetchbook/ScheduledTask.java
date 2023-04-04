package com.bathanh.apibook.domain.fetchbook;

import com.bathanh.apibook.domain.book.BookService;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class ScheduledTask {

    private final BookService bookService;
    private Scheduler scheduler;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @PostConstruct
    public void init() throws SchedulerException {
        scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
    }

    @Scheduled(fixedRate = 600000)
    public void storeNewBooksPeriodically() throws SchedulerException {
        if (scheduler.isStarted()) {
            logger.info("Scheduler has been started");
        } else {
            logger.info("Scheduler has not been started");
        }

        bookService.storeNewBooks();
        logger.info("Fetch new books from other API successfully");
    }
}
