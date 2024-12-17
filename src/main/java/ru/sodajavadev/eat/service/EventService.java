package ru.sodajavadev.eat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sodajavadev.eat.entity.Event;
import ru.sodajavadev.eat.entity.EventTemplate;
import ru.sodajavadev.eat.repository.EventRepository;
import ru.sodajavadev.eat.repository.EventTemplateRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * Сервис для работы с event
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventTemplateRepository eventTemplateRepository;

    @Transactional
    public void processEvent(Integer minute, Integer hour, DayOfWeek dayOfWeek, Integer dayOfMonth) {
        var date = LocalDateTime.now();
        eventTemplateRepository.findForCurrentTime(minute, hour, dayOfWeek, dayOfMonth).stream().map(t -> eventBuilder(t, date)).forEach(eventRepository::save);
    }

    private static Event eventBuilder(EventTemplate eventTemplate, LocalDateTime date) {
        var event = new Event();
        event.setEventName(eventTemplate.getEventName());
        event.setEventTime(date);
        event.setEventTemplate(eventTemplate);
        return event;
    }

}
