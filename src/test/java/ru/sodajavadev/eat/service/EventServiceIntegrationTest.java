package ru.sodajavadev.eat.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.sodajavadev.eat.entity.EventTemplate;
import ru.sodajavadev.eat.entity.EventTemplateType;
import ru.sodajavadev.eat.repository.EventRepository;
import ru.sodajavadev.eat.repository.EventTemplateRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class EventServiceIntegrationTest {

    @Autowired
    EventTemplateRepository eventTemplateRepository;

    @Autowired
    EventService service;

    @Autowired
    EventRepository eventRepository;

    @ParameterizedTest
    @CsvSource({
            "DAILY",
            "WEEKLY",
            "MONTHLY"
    })
    void processEvent(EventTemplateType type) {
        var eventTemplateTest = createEventTemplateTest(type);
        eventTemplateRepository.save(eventTemplateTest);
        eventTemplateRepository.save(createNoMatchingEventTemplateTest(type));

        LocalDateTime localDateTime = LocalDateTime.now();
        service.processEvent(localDateTime.getMinute(),
                localDateTime.getHour(),
                localDateTime.getDayOfWeek(),
                localDateTime.getDayOfMonth());

        var event = eventRepository.findAll().stream()
                .findFirst();

        assertTrue(event.isPresent());
        assertEquals(eventRepository.findAll().size(), 1);
        assertEquals(eventTemplateRepository.findAll().size(), 2);

        assertEquals(eventTemplateTest.getId(), event.get().getEventTemplate().getId());
        assertEquals(eventTemplateTest.getEventName(), event.get().getEventName());
        assertEquals(eventTemplateTest.getMinute(), event.get().getEventTime().getMinute());
        assertEquals(eventTemplateTest.getHour(), event.get().getEventTime().getHour());
        assertEquals(eventTemplateTest.getDayOfWeek(), event.get().getEventTime().getDayOfWeek());
        assertEquals(eventTemplateTest.getDayOfMonth(), event.get().getEventTime().getDayOfMonth());
    }

    private EventTemplate createEventTemplateTest(EventTemplateType type) {
        return EventTemplate.builder()
                .templateName("test-1")
                .eventName("test")
                .type(type)
                .minute(LocalDateTime.now().getMinute())
                .hour(LocalDateTime.now().getHour())
                .dayOfWeek(LocalDateTime.now().getDayOfWeek())
                .dayOfMonth(LocalDateTime.now().getDayOfMonth())
                .active(true)
                .build();
    }

    private EventTemplate createNoMatchingEventTemplateTest(EventTemplateType type) {
        return EventTemplate.builder()
                .templateName("incorrect-test-2")
                .eventName("incorrect-test")
                .type(type)
                .minute(LocalDateTime.now().getMinute() + 1)
                .hour(LocalDateTime.now().getHour() + 1)
                .dayOfWeek(LocalDateTime.now().getDayOfWeek().plus(1L))
                .dayOfMonth(LocalDateTime.now().getDayOfMonth() + 1)
                .active(true)
                .build();
    }
}