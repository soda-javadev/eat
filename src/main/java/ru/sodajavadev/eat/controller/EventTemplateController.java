package ru.sodajavadev.eat.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.sodajavadev.eat.dto.EventTemplateDto;
import ru.sodajavadev.eat.service.EventTemplateService;

import java.util.List;

import static ru.sodajavadev.eat.controller.EventTemplateController.UI_V_1_EVENT_TEMPLATE;

@RequiredArgsConstructor
@RequestMapping(value = UI_V_1_EVENT_TEMPLATE)
@RestController
@Validated
public class EventTemplateController {

    protected static final String UI_V_1_EVENT_TEMPLATE = "/ui/v1/event-template";

    private final EventTemplateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventTemplateDto createEventTemplate(@Valid @RequestBody EventTemplateDto eventTemplate) {

        return service.createEventTemplate(eventTemplate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public EventTemplateDto findById(@RequestParam Long id) {

        return service.findById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<EventTemplateDto> findAll(@RequestParam(required = false) Boolean onlyActive) {

        return service.findAll(onlyActive);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EventTemplateDto updateEventTemplateDto(@Valid @RequestBody EventTemplateDto eventTemplateDto) {

        return service.updateEventTemplateDto(eventTemplateDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestParam Long id) {

        service.deleteById(id);
    }
}
