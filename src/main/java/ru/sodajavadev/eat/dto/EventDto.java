package ru.sodajavadev.eat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;
    private String eventName;
    private LocalDateTime eventTime;
    private Boolean successfully;
    private Long eventTemplateId;
}
