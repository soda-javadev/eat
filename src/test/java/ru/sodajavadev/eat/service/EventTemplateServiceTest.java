package ru.sodajavadev.eat.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sodajavadev.eat.dto.EventTemplateDto;
import ru.sodajavadev.eat.entity.EventTemplate;
import ru.sodajavadev.eat.entity.EventTemplateType;
import ru.sodajavadev.eat.exception.EventTemplateBaseException;
import ru.sodajavadev.eat.mapstruct.EventTemplateMapper;
import ru.sodajavadev.eat.repository.EventTemplateRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventTemplateServiceTest {

    protected static final Long ID = 1L;

    protected static final String INCORRECT_EVENT_TEMPLATE_ID = "События с id - 1 не существует";

    protected static final String FIELD_ID = "id";

    @Mock
    private EventTemplateRepository repositoryMock;

    @Spy
    private EventTemplateMapper mapper = Mappers.getMapper(EventTemplateMapper.class);

    @InjectMocks
    private EventTemplateService service;

    @Test
    void createEventTemplate() {

        EventTemplate expectedResult = createEventTemplateTest();

        when(repositoryMock.save(any(EventTemplate.class)))
                .thenReturn(expectedResult);

        EventTemplateDto actualResult = service.createEventTemplate(createEventTemplateDtoTest());

        verify(mapper).toDto(expectedResult);

        assertEquals(mapper.toDto(expectedResult), actualResult);
    }

    @ParameterizedTest
    @CsvSource({
            "WEEKLY, MONDAY, ",
            "MONTHLY, , 1"
    })
    void validateEventTemplateTypeWeeklyOrMonthly(EventTemplateType type, DayOfWeek dayOfWeek, Integer dayOfMonth) {

        EventTemplateDto eventTemplateDtoTest = createEventTemplateDtoTest();
        eventTemplateDtoTest.setType(type);
        eventTemplateDtoTest.setDayOfWeek(dayOfWeek);
        eventTemplateDtoTest.setDayOfMonth(dayOfMonth);

        assertDoesNotThrow(() -> service.validateEventTemplateType(eventTemplateDtoTest));
    }

    @ParameterizedTest
    @CsvSource({
            "WEEKLY, , 1",
            "MONTHLY, MONDAY, "
    })
    void validateEventTemplateTypeWeeklyOrMonthlyWithInvalidDay(EventTemplateType type, DayOfWeek dayOfWeek, Integer dayOfMonth) {

        EventTemplateDto eventTemplateDtoTest = createEventTemplateDtoTest();
        eventTemplateDtoTest.setType(type);
        eventTemplateDtoTest.setDayOfWeek(dayOfWeek);
        eventTemplateDtoTest.setDayOfMonth(dayOfMonth);

        var actualError = assertThrows(EventTemplateBaseException.class, () -> service.validateEventTemplateType(eventTemplateDtoTest));

        if (type.equals(EventTemplateType.WEEKLY)) {

            assertEquals("При указании типа события - Еженедельно, день недели должен быть задан", actualError.getMessage());
            assertEquals("dayOfWeek", actualError.getField());
        }

        if (type.equals(EventTemplateType.MONTHLY)) {

            assertEquals("При указании типа события - Ежемесячно, день месяца должен быть задан", actualError.getMessage());
            assertEquals("dayOfMonth", actualError.getField());
        }
    }

    @Test
    void findById() {

        EventTemplate expectedResult = createEventTemplateTest();

        when(repositoryMock.findById(ID))
                .thenReturn(Optional.of(expectedResult));

        EventTemplateDto actualResult = service.findById(ID);

        verify(mapper).toDto(expectedResult);

        assertEquals(mapper.toDto(expectedResult), actualResult);
    }

    @Test
    void findByIdWhenEntityDoesNotExist() {

        when(repositoryMock.findById(ID))
                .thenReturn(Optional.empty());

        var exception = assertThrows(EventTemplateBaseException.class, () -> service.findById(ID));

        assertEquals(INCORRECT_EVENT_TEMPLATE_ID, exception.getMessage());
        assertEquals(FIELD_ID, exception.getField());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true})
    @NullSource
    void findAllWhenIsActiveTrueOrNull(Boolean isActive) {

        List<EventTemplate> expectedResult = List.of(createEventTemplateTest(), createEventTemplateTest());

        when(repositoryMock.findAllByActiveIsTrue())
                .thenReturn(expectedResult);

        List<EventTemplateDto> actualResult = service.findAll(isActive);

        verify(mapper).toListDto(expectedResult);

        assertEquals(2, actualResult.size());
    }

    @Test
    void findAllWhenIsActiveFalse() {

        List<EventTemplate> expectedResult = List.of(createEventTemplateTest(), createEventTemplateTest());

        when(repositoryMock.findAll())
                .thenReturn(expectedResult);

        List<EventTemplateDto> actualResult = service.findAll(false);

        verify(mapper).toListDto(expectedResult);

        assertEquals(2, actualResult.size());
    }

    @Test
    void updateEventTemplateDto() {

        EventTemplate expectedResult = createEventTemplateTest();

        EventTemplateDto dto = createEventTemplateDtoTest();

        when(repositoryMock.findById(dto.getId()))
                .thenReturn(Optional.of(expectedResult));

        when(repositoryMock.save(any(EventTemplate.class)))
                .thenReturn(expectedResult);

        EventTemplateDto actualResult = service.updateEventTemplateDto(dto);

        verify(mapper).toDto(expectedResult);

        assertEquals(mapper.toDto(expectedResult), actualResult);
    }

    @Test
    void updateEventTemplateDtoWithIncorrectId() {

        EventTemplateDto dto = createEventTemplateDtoTest();
        dto.setId(1L);

        when(repositoryMock.findById(dto.getId()))
                .thenReturn(Optional.empty());

        var e = assertThrows(EventTemplateBaseException.class, () -> service.updateEventTemplateDto(dto));

        assertEquals(INCORRECT_EVENT_TEMPLATE_ID, e.getMessage());
        assertEquals(FIELD_ID, e.getField());
    }

    @ParameterizedTest
    @CsvSource({
            "DAILY,",
            "WEEKLY,",
            "MONTHLY"
    })
    void mapEventTemplateFunctionByTypeWithCorrectEventTemplateType(EventTemplateType type) {

        EventTemplateDto eventTemplateDtoTest = createEventTemplateDtoTest();
        eventTemplateDtoTest.setType(type);


        assertDoesNotThrow(() -> service.mapEventTemplateFunctionByType(eventTemplateDtoTest).apply(new EventTemplate()));

        switch (type) {
            case DAILY -> verify(mapper).mapToDaily(any(EventTemplateDto.class), any(EventTemplate.class));
            case WEEKLY -> verify(mapper).mapToWeekly(any(EventTemplateDto.class), any(EventTemplate.class));
            case MONTHLY -> verify(mapper).mapToMonthly(any(EventTemplateDto.class), any(EventTemplate.class));
        }
    }

    @Test
    void mapEventTemplateFunctionByTypeWithIncorrectEventTemplateType() {

        EventTemplateDto eventTemplateDtoTest = createEventTemplateDtoTest();
        eventTemplateDtoTest.setType(null);

        var actualError = assertThrows(EventTemplateBaseException.class, () -> service.mapEventTemplateFunctionByType(eventTemplateDtoTest));

        assertEquals(actualError.getMessage(), "Типа события null должен быть задан");
        assertEquals(actualError.getField(), "type");
    }


    @Test
    void deleteByIdSuccessful() {

        Mockito.when(repositoryMock.deleteByEventTemplateId(ID))
                .thenReturn(1);

        assertDoesNotThrow(() -> service.deleteById(ID));
    }

    @Test
    void deleteByIdWithMissingEntity() {

        Mockito.when(repositoryMock.deleteByEventTemplateId(ID))
                .thenReturn(0);

        var exception = assertThrows(EventTemplateBaseException.class, () -> service.deleteById(ID));

        assertEquals(INCORRECT_EVENT_TEMPLATE_ID, exception.getMessage());
        assertEquals(FIELD_ID, exception.getField());
    }


    EventTemplateDto createEventTemplateDtoTest() {

        return EventTemplateDto.builder()
                .templateName("test")
                .eventName("test")
                .type(EventTemplateType.DAILY)
                .minute(1)
                .hour(1)
                .active(true)
                .build();
    }

    EventTemplate createEventTemplateTest() {

        return EventTemplate.builder()
                .templateName("test")
                .eventName("test")
                .type(EventTemplateType.DAILY)
                .minute(1)
                .hour(1)
                .active(true)
                .build();
    }
}