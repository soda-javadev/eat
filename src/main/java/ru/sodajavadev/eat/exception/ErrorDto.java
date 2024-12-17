package ru.sodajavadev.eat.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Ошибка")
public class ErrorDto {

    @Schema(description = "Название поля", example = "id")
    private String field;
    @Schema(description = "Текст ошибки", example = "События с id - 1 не существует")
    private String message;
}
