package com.bathanh.apibook.integration;

import com.bathanh.apibook.domain.book.ItBookService;
import com.bathanh.apibook.jobs.ScheduledTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.quartz.SchedulerException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskTest {

    @Mock
    private ItBookService itBookService;

    @InjectMocks
    private ScheduledTask scheduledTask;

    @BeforeEach
    void setUp() throws SchedulerException {
        scheduledTask.init();
    }

    @Test
    void shouldStoreNewBooksPeriodically_Ok() {
        doNothing().when(itBookService).storeNewBooks();
        scheduledTask.storeNewBooksPeriodically();
        verify(itBookService, times(1)).storeNewBooks();
    }

    @Test
    void shouldStoreNewBooksPeriodically_WhenException() {
        doThrow(new RuntimeException()).when(itBookService).storeNewBooks();
        scheduledTask.storeNewBooksPeriodically();
        verify(itBookService, times(1)).storeNewBooks();
    }

}