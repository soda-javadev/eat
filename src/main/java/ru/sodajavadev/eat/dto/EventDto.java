package ru.sodajavadev.eat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Событие")
public class EventDto {

    @Schema(description = "Уникальный идентификатор", example = "1")
    private Long id;

    @Schema(description = "Название события", example = "AGL")
    private String eventName;

    @Schema(description = "Время начала", example = "18:20")
    private LocalDateTime eventTime;

    @Schema(description = "Считается ли успешным", example = "true")
    private Boolean successfully;

    @Schema(description = "Уникальный идентификатор шаблона события", example = "1")
    private Long eventTemplateId;
}
