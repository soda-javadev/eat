package ru.sodajavadev.eat.exception;

import io.swagger.v3.oas.annotations.Hidden;
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
    @Hidden
    public List<ErrorDto> handleEventTemplateException(ConstraintViolationException e) {
        return (e.getConstraintViolations().stream()
                .map(errorDto -> new ErrorDto(
                        errorDto.getPropertyPath().toString(),
                        errorDto.getMessage()))
                .collect(toList()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public List<ErrorDto> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return (e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(errorDto -> new ErrorDto(
                        errorDto.getField(),
                        errorDto.getDefaultMessage()))
                .collect(toList()));
    }

    @ResponseBody
    @ExceptionHandler({EventTemplateBaseException.class, Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
    public ErrorDto valid(EventTemplateBaseException e) {
        log.error(e.getMessage());

        return new ErrorDto(e.getField(), e.getMessage());
    }
}
