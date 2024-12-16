package ru.sodajavadev.eat.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    @Schema(description = "Название поля ошибки", example = "id")
    private String field;
    @Schema(description = "Текст произошедшей ошибки", example = "События с id - 1 не существует")
    private String message;
}
