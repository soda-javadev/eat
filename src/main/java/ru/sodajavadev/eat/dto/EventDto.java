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

    private Long id;
    private String eventName;
    private LocalDateTime eventTime;
    private Boolean successfully;
    private Long eventTemplateId;
}
