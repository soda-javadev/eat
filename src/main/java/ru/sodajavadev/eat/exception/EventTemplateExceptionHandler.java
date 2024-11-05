package ru.sodajavadev.eat.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.util.stream.Collectors.toList;

@Slf4j
@ControllerAdvice(basePackages = "ru.sodajavadev")
public class EventTemplateExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EventTemplateErrorDto handleEventTemplateException(ConstraintViolationException e) {

        return new EventTemplateErrorDto(e.getConstraintViolations().stream()
                .map(errorDto -> new ErrorDto(
                        errorDto.getPropertyPath().toString(),
                        errorDto.getMessage()))
                .collect(toList()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public EventTemplateErrorDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return new EventTemplateErrorDto(e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(errorDto -> new ErrorDto(
                        errorDto.getField(),
                        errorDto.getDefaultMessage()))
                .collect(toList()));
    }

    @ResponseBody
    @ExceptionHandler(EventTemplateBaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto valid(EventTemplateBaseException e) {

        log.error(e.getMessage());

        return new ErrorDto(e.getField(), e.getMessage());
    }
}
