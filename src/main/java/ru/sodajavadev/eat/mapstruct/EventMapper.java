package ru.sodajavadev.eat.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.sodajavadev.eat.dto.EventDto;
import ru.sodajavadev.eat.entity.Event;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EventMapper {

    @Mapping(target = "eventTemplate.id", source = "eventTemplateId")
    @Mapping(target = "loot", ignore = true)
    @Mapping(target = "guildMembers", ignore = true)
    Event toEntity(EventDto event);

    @Mapping(target = "eventTemplateId", source = "eventTemplate.id")
    EventDto toDto(Event event);
}
