package ru.sodajavadev.eat.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sodajavadev.eat.service.EventService;

import java.time.LocalDateTime;

@Component
@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class EventScheduler {

    private final EventService eventService;

    @Scheduled(cron = "${scheduler.event}")
    @Transactional
    public void run() {
        log.info("EventScheduler event creation start");

        LocalDateTime localDateTime = LocalDateTime.now();
        eventService.processEvent(localDateTime.getMinute(), localDateTime.getHour(), localDateTime.getDayOfWeek(), localDateTime.getDayOfMonth());

        log.info("EventScheduler event creation end");
    }
}
