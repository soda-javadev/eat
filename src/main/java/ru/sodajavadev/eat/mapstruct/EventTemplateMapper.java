package ru.sodajavadev.eat.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.sodajavadev.eat.dto.EventTemplateDto;
import ru.sodajavadev.eat.entity.EventTemplate;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EventTemplateMapper {

    EventTemplateDto toDto(EventTemplate eventTemplate);

    EventTemplate toEntity(EventTemplateDto eventTemplate);

    @Mapping(target = "id", ignore = true)
    EventTemplate toNewEntity(EventTemplateDto eventTemplate);

    List<EventTemplateDto> toListDto(List<EventTemplate> sourceList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dayOfWeek", expression = "java(null)")
    @Mapping(target = "dayOfMonth",  expression = "java(null)")
    EventTemplate mapToDaily(EventTemplateDto fromObject, @MappingTarget EventTemplate toObject);

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "dayOfMonth",  expression = "java(null)")
    EventTemplate mapToWeekly(EventTemplateDto fromObject, @MappingTarget EventTemplate toObject);

    @Mapping(target = "id",  ignore = true)
    @Mapping(target = "dayOfWeek",  expression = "java(null)")
    EventTemplate mapToMonthly(EventTemplateDto fromObject, @MappingTarget EventTemplate toObject);
}
