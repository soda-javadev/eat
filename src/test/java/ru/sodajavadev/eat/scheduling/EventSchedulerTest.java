package ru.sodajavadev.eat.scheduling;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.sodajavadev.eat.service.EventService;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.TWO_SECONDS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = "scheduler.event=0/1 * * * * *")
class EventSchedulerTest {

    @Autowired
    private EventScheduler scheduler;
    @MockBean
    private EventService eventService;

    @Test
    void run() {
        Mockito.spy(scheduler);
        await()
                .atMost(TWO_SECONDS)
                .untilAsserted(() -> verify(eventService, atLeast(1))
                        .processEvent(any(), any(), any(), any()));
    }
}
