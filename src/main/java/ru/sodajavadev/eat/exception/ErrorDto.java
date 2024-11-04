package ru.sodajavadev.eat.exception;

import lombok.Builder;

@Builder
public record ErrorDto(String field, String message) {

}
