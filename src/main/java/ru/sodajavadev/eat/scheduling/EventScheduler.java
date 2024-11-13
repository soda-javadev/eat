package ru.sodajavadev.eat.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.sodajavadev.eat.dto.EventDto;
import ru.sodajavadev.eat.entity.EventTemplate;
import ru.sodajavadev.eat.repository.EventTemplateRepository;
import ru.sodajavadev.eat.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
@Slf4j
public class EventScheduler {

    private final EventTemplateRepository repository;
    private final EventService eventService;

    @Scheduled(cron = "0 * * * * *")
    public void run() {
        log.info("Start");

        LocalDateTime localDateTime = LocalDateTime.now();
        List<EventTemplate> all = repository.findAll(localDateTime.getMinute(), localDateTime.getHour(), localDateTime.getDayOfWeek(), localDateTime.getDayOfMonth());

        for (EventTemplate eventTemplate : all) {
            eventService.createEvent(new EventDto(eventTemplate.getId(), eventTemplate.getEventName(), LocalDateTime.now(), false, eventTemplate.getId()));
        }

        log.info("End");
    }
}
