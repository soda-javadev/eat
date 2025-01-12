package ru.sodajavadev.eat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.sodajavadev.eat.exception.ErrorDto;
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
    @PreAuthorize("hasRole('GM') or hasRole('OFFICER')")
    @Operation(summary = "Создание нового")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно создан",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Данные введены некорректно", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorDto.class))))
    })
    public EventTemplateDto createEventTemplate(@Valid @RequestBody EventTemplateDto eventTemplate) {
        return service.createEventTemplate(eventTemplate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('GM') or hasRole('OFFICER') or hasRole('MEMBER')")
    @Operation(summary = "Поиск по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно найден",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Переданный id не существует", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDto.class))),
    })
    public EventTemplateDto findById(@RequestParam Long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('GM') or hasRole('OFFICER') or hasRole('MEMBER')")
    @Operation(summary = "Поиск всех")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно найдены",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class)))
    })
    public List<EventTemplateDto> findAll(@RequestParam(required = false) @Parameter(description = "False - возвращает все без фильтрации по статусу активен") Boolean onlyActive) {
        return service.findAll(onlyActive);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('GM') or hasRole('OFFICER')")
    @Operation(summary = "Для обновления")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно обновлен",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "Не удалось обновить из-за некорректности введенных данных", content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ErrorDto.class))))
    })
    public EventTemplateDto updateEventTemplateDto(@Valid @RequestBody EventTemplateDto eventTemplateDto) {
        return service.updateEventTemplateDto(eventTemplateDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('GM')")
    @Operation(summary = "Для удаления по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно удален",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EventTemplateDto.class))),
            @ApiResponse(responseCode = "400", description = "С переданным id не существует", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorDto.class)))
    })
    public void deleteById(@RequestParam Long id) {
        service.deleteById(id);
    }
}
