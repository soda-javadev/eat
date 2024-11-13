package ru.sodajavadev.eat.exception;

import lombok.Getter;

@Getter
public class EventTemplateBaseException extends RuntimeException {

    private final String field;

    public EventTemplateBaseException(String message, String field) {
        super(message);
        this.field = field;
    }
}
