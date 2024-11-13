package ru.sodajavadev.eat.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sodajavadev.eat.dto.EventTemplateDto;
import ru.sodajavadev.eat.entity.EventTemplate;
import ru.sodajavadev.eat.entity.EventTemplateType;
import ru.sodajavadev.eat.exception.EventTemplateBaseException;
import ru.sodajavadev.eat.mapstruct.EventTemplateMapper;
import ru.sodajavadev.eat.repository.EventTemplateRepository;

import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EventTemplateService {

    protected static final String INCORRECT_EVENT_TEMPLATE_ID = "События с id - %d не существует";
    protected static final String ID = "id";
    protected static final String DAY_OF_WEEK = "dayOfWeek";
    protected static final String INCORRECT_DAY_WEEK = "При указании типа события - Еженедельно, день недели должен быть задан";
    protected static final String DAY_OF_MONTH = "dayOfMonth";
    protected static final String INCORRECT_DAY_MONTH = "При указании типа события - Ежемесячно, день месяца должен быть задан";
    protected static final String INCORRECT_EVENT_TEMPLATE_NAME = "Такое имя шаблона события уже существует, задайте новое";
    protected static final String EVENT_TEMPLATE_NAME = "eventTemplateName";
    protected static final String INCORRECT_EVENT_TEMPLATE_TYPE = "Типа события %s должен быть задан";
    protected static final String TYPE = "type";

    private final EventTemplateRepository repository;
    private final EventTemplateMapper mapper;

    @SneakyThrows
    @Transactional
    public EventTemplateDto createEventTemplate(EventTemplateDto eventTemplateDto) {
//        TODO добавить EventTemplateType - ONCE, создать метод для создания EventTemplate и Event сразу по этому типу
        if (EventTemplateType.ONCE.equals(eventTemplateDto.getType())) {
            throw new EventTemplateBaseException("Еще не реализован", TYPE);
        }

        validateEventTemplateType(eventTemplateDto);

        validateEventTemplateName(eventTemplateDto);

        return mapper.toDto(repository.save(mapEventTemplateFunctionByType(eventTemplateDto).apply(new EventTemplate())));
    }

    @SneakyThrows
    @Transactional
    public EventTemplateDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EventTemplateBaseException(format(INCORRECT_EVENT_TEMPLATE_ID, id), ID));
    }

    @Transactional
    public List<EventTemplateDto> findAll(Boolean onlyActive) {
        if (onlyActive != null && !onlyActive) {
            return mapper.toListDto(repository.findAll());
        }

        return mapper.toListDto(repository.findAllByActiveIsTrue());
    }

    @SneakyThrows
    @Transactional
    public EventTemplateDto updateEventTemplateDto(EventTemplateDto eventTemplateDto) {
        validateEventTemplateType(eventTemplateDto);

        return repository.findById(eventTemplateDto.getId())
                .map(mapEventTemplateFunctionByType(eventTemplateDto))
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new EventTemplateBaseException(format(INCORRECT_EVENT_TEMPLATE_ID, eventTemplateDto.getId()), ID));
    }

    @SneakyThrows
    @Transactional
    public void deleteById(Long id) {
        if (repository.deleteByEventTemplateId(id) == 0) {
            throw new EventTemplateBaseException(format(INCORRECT_EVENT_TEMPLATE_ID, id), ID);
        }
    }

    protected void validateEventTemplateType(EventTemplateDto eventTemplateDto) {
        if (EventTemplateType.WEEKLY == (eventTemplateDto.getType()) && eventTemplateDto.getDayOfWeek() == null) {
            throw new EventTemplateBaseException(INCORRECT_DAY_WEEK, DAY_OF_WEEK);
        }

        if (EventTemplateType.MONTHLY == (eventTemplateDto.getType()) && eventTemplateDto.getDayOfMonth() == null) {
            throw new EventTemplateBaseException(INCORRECT_DAY_MONTH, DAY_OF_MONTH);
        }
    }

    protected void validateEventTemplateName(EventTemplateDto eventTemplateDto) {
        if (repository.isEventTemplateNameExists(eventTemplateDto.getTemplateName())) {
            throw new EventTemplateBaseException(INCORRECT_EVENT_TEMPLATE_NAME, EVENT_TEMPLATE_NAME);
        }
    }

    protected Function<EventTemplate, EventTemplate> mapEventTemplateFunctionByType(EventTemplateDto eventTemplateDto) {
        if (EventTemplateType.DAILY == eventTemplateDto.getType()) {
            return eventTemplate -> mapper.mapToDaily(eventTemplateDto, eventTemplate);
        } else if (EventTemplateType.WEEKLY == eventTemplateDto.getType()) {
            return eventTemplate -> mapper.mapToWeekly(eventTemplateDto, eventTemplate);
        } else if (EventTemplateType.MONTHLY == eventTemplateDto.getType()) {
            return eventTemplate -> mapper.mapToMonthly(eventTemplateDto, eventTemplate);
        } else {
            throw new EventTemplateBaseException(format(INCORRECT_EVENT_TEMPLATE_TYPE, eventTemplateDto.getType()), TYPE);
        }
    }
}