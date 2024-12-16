package ru.sodajavadev.eat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sodajavadev.eat.entity.EventTemplateType;

import java.time.DayOfWeek;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Сущность шаблона события")
public class EventTemplateDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Название шаблона события", example = "AGL 18:20")
    private String templateName;

    @Schema(description = "Название события", example = "AGL")
    private String eventName;

    @Schema(description = "Тип шаблона события", example = "DAILY")
    private EventTemplateType type;

    @NotNull
    @Min(value = 0, message = "Минуты должны быть в диапазоне от 0 до 59")
    @Max(value = 59, message = "Минуты должны быть в диапазоне от 0 до 59")
    @Schema(description = "Минуты начала шаблона события", example = "20")
    private Integer minute;

    @NotNull
    @Min(value = 0, message = "Часы должны быть в диапазоне от 0 до 23")
    @Max(value = 23, message = "Часы должны быть в диапазоне от 0 до 23")
    @Schema(description = "Часы начала шаблона события", example = "18")
    private Integer hour;

    @Schema(description = "День недели шаблона события в случае типа события WEEKLY", example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Min(value = 1, message = "День месяца должен быть в диапазоне от 1 до 31")
    @Max(value = 31, message = "День месяца должен быть в диапазоне от 1 до 31")
    @Schema(description = "День месяца шаблона события в случае типа события MONTHLY", example = "20")
    private Integer dayOfMonth;

    @NotNull
    @Schema(description = "Активность шаблона события", example = "true")
    private Boolean active;
}
