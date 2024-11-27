package ru.sodajavadev.eat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sodajavadev.eat.entity.EventTemplate;
import ru.sodajavadev.eat.entity.EventTemplateType;
import ru.sodajavadev.eat.repository.EventRepository;
import ru.sodajavadev.eat.repository.EventTemplateRepository;

import java.time.DayOfWeek;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    protected static final int MINUTE = 1;
    protected static final int HOUR = 1;
    protected static final DayOfWeek DAY_OF_WEEK = DayOfWeek.MONDAY;
    protected static final int DAY_OF_MONTH = 12;

    @Mock
    private EventRepository eventRepositoryMock;

    @Mock
    private EventTemplateRepository eventTemplateRepositoryMock;

    @Spy
    @InjectMocks
    private EventService service;

    @Test
    void processEvent() {
        EventTemplate eventTemplateTest1 = createEventTemplateTest();
        EventTemplate eventTemplateTest2 = createEventTemplateTest();
        EventTemplate eventTemplateTest3 = createEventTemplateTest();
        List<EventTemplate> expectedListResult = List.of(eventTemplateTest1, eventTemplateTest2, eventTemplateTest3);
        when(eventTemplateRepositoryMock.findForCurrentTime(anyInt(), anyInt(), any(DayOfWeek.class), anyInt()))
                .thenReturn(expectedListResult);

        service.processEvent(MINUTE, HOUR, DAY_OF_WEEK, DAY_OF_MONTH);

        verify(eventRepositoryMock, times(expectedListResult.size())).save(any());
    }

    private EventTemplate createEventTemplateTest() {
        return EventTemplate.builder()
                .id(1L)
                .templateName("test-1")
                .eventName("test")
                .type(EventTemplateType.DAILY)
                .minute(MINUTE)
                .hour(HOUR)
                .dayOfWeek(DAY_OF_WEEK)
                .dayOfMonth(DAY_OF_MONTH)
                .active(true)
                .build();
    }
}