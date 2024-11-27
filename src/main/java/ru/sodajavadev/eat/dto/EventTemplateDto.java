package ru.sodajavadev.eat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
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
public class EventTemplateDto {

    private Long id;
    private String templateName;
    private String eventName;
    private EventTemplateType type;

    @NotNull
    @Min(value = 0, message = "Минуты должны быть в диапазоне от 0 до 59")
    @Max(value = 59, message = "Минуты должны быть в диапазоне от 0 до 59")
    private Integer minute;

    @NotNull
    @Min(value = 0, message = "Часы должны быть в диапазоне от 0 до 23")
    @Max(value = 23, message = "Часы должны быть в диапазоне от 0 до 23")
    private Integer hour;

    private DayOfWeek dayOfWeek;

    @Min(value = 1, message = "День месяца должен быть в диапазоне от 1 до 31")
    @Max(value = 31, message = "День месяца должен быть в диапазоне от 1 до 31")
    private Integer dayOfMonth;

    @NotNull
    private Boolean active;
}
