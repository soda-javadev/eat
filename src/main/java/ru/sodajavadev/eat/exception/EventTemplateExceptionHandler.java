package ru.sodajavadev.eat.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice(basePackages = "ru.sodajavadev")
public class EventTemplateExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EventTemplateErrorDto handleEventTemplateException(ConstraintViolationException e) {

        final List<ErrorDto> errorDtos = e.getConstraintViolations().stream()
                .map(errorDto -> new ErrorDto(
                        errorDto.getPropertyPath().toString(),
                        errorDto.getMessage())
                )
                .collect(toList());
        return new EventTemplateErrorDto(errorDtos);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EventTemplateErrorDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        final List<ErrorDto> errorDtos = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(errorDto -> new ErrorDto(errorDto.getField(),
                        errorDto.getDefaultMessage())
                )
                .collect(toList());
        return new EventTemplateErrorDto(errorDtos);
    }

    @ResponseBody
    @ExceptionHandler(EventTemplateBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto valid(EventTemplateBaseException e) {

        log.error(e.getMessage());

        return ErrorDto.builder()
                .message(e.getMessage())
                .field(e.getField())
                .build();
    }
}
