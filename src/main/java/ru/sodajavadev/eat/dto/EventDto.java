package ru.sodajavadev.eat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String eventName;
    private LocalDateTime eventTime;
    private Boolean successfully;
    private Long eventTemplateId;
}
