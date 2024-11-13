package ru.sodajavadev.eat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sodajavadev.eat.dto.EventDto;
import ru.sodajavadev.eat.mapstruct.EventMapper;
import ru.sodajavadev.eat.repository.EventRepository;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;
    private final EventMapper mapper;

    public void createEvent(EventDto eventDto) {
        repository.save(mapper.toEntity(eventDto));
    }
}
