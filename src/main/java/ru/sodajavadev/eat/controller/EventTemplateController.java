package ru.sodajavadev.eat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
import ru.sodajavadev.eat.exception.EventTemplateErrorDto;
import ru.sodajavadev.eat.service.EventTemplateService;

import java.util.List;

import static ru.sodajavadev.eat.controller.EventTemplateController.UI_V_1_EVENT_TEMPLATE;

@RequiredArgsConstructor
@RequestMapping(value = UI_V_1_EVENT_TEMPLATE)
@RestController
@Tag(name = "Контроллер для управления шаблонами событий")
public class EventTemplateController {

    protected static final String UI_V_1_EVENT_TEMPLATE = "/ui/v1/event-template";

    private final EventTemplateService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Метод для создания нового шаблона событий", description = "Позволяет создать новый шаблон события")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Шаблон события успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Данные введены некорректно", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventTemplateErrorDto.class)))
    })
    public EventTemplateDto createEventTemplate(@Valid @RequestBody EventTemplateDto eventTemplate) {
        return service.createEventTemplate(eventTemplate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Метод для поиска шаблона события по id", description = "Позволяет найти нужный шаблон события по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Шаблон события успешно найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Шаблона события с введенным id не существует", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventTemplateErrorDto.class))),
    })
    public EventTemplateDto findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Метод для поиска всех шаблонов событий в зависимости от переданного параметра", description = "Позволяет найти все нужные события")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "События успешно найдены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
    })
    public List<EventTemplateDto> findAll(@RequestParam(required = false) Boolean onlyActive) {
        return service.findAll(onlyActive);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Метод для обновления шаблона события", description = "Позволяет обновить нужный шаблон события")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Шаблон события успешно обновлен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Шаблон события не удалось обновить из-за некорректности введенных данных", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventTemplateErrorDto.class)))
    })
    public EventTemplateDto updateEventTemplateDto(@Valid @RequestBody EventTemplateDto eventTemplateDto) {
        return service.updateEventTemplateDto(eventTemplateDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Метод для удаления шаблона события по id", description = "Позволяет удалить нужный шаблон события по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Шаблон события успешно удален",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Шаблон события с введенным id не существует", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EventTemplateErrorDto.class)))
    })
    public void deleteById(@RequestParam Long id) {
        service.deleteById(id);
    }
}
