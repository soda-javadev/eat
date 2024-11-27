package ru.sodajavadev.eat.scheduling;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.sodajavadev.eat.configuration.SchedulerConfig;

import static org.awaitility.Awaitility.await;
import static org.awaitility.Durations.ONE_MINUTE;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@SpringJUnitConfig(SchedulerConfig.class)
class EventSchedulerTest {

    @MockBean
    private EventScheduler scheduler;

    @Test
    void run() {
        await()
                .atMost(ONE_MINUTE)
                .untilAsserted(() -> verify(scheduler, atLeast(1)).run());
    }
}