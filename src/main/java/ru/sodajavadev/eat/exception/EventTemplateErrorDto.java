package ru.sodajavadev.eat.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class EventTemplateErrorDto {

    private final List<ErrorDto> errorDtos;
}
