package ru.sodajavadev.eat.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.sodajavadev.eat.dto.EventTemplateDto;
import ru.sodajavadev.eat.entity.EventTemplateType;

public class CheckValidEventTemplateDto implements ConstraintValidator<ValidEventTemplateDto, EventTemplateDto> {

    @Override
    public void initialize(ValidEventTemplateDto constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EventTemplateDto eventTemplateDto, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        boolean isValid = true;

        if (eventTemplateDto.getTemplateName() == null) {

            context.buildConstraintViolationWithTemplate("Имя шаблона события должно быть задано.")
                    .addConstraintViolation();

            isValid = false;

        }

        if (eventTemplateDto.getEventName() == null) {

            context.buildConstraintViolationWithTemplate("Имя события должно быть задано.")
                    .addConstraintViolation();

            isValid = false;

        }
        if (eventTemplateDto.getType() == null) {

            context.buildConstraintViolationWithTemplate("Тип события должен быть задан.")
                    .addConstraintViolation();

            isValid = false;

        }

        if (eventTemplateDto.getMinute() == null) {

            context.buildConstraintViolationWithTemplate("Минуты события должны быть заданы.")
                    .addConstraintViolation();

            isValid = false;

        }

        if (eventTemplateDto.getHour() == null) {

            context.buildConstraintViolationWithTemplate("Часы события должны быть заданы.")
                    .addConstraintViolation();

            isValid = false;

        }

        if (eventTemplateDto.getActive() == null) {

            context.buildConstraintViolationWithTemplate("Активность события должна быть задана.")
                    .addConstraintViolation();

            isValid = false;

        }

        isValid = switch (eventTemplateDto.getType()) {
            case DAILY -> isValid;
            case WEEKLY -> validateWeekly(eventTemplateDto, context);
            case MONTHLY -> validateMonthly(eventTemplateDto, context);
        };

        return isValid;
    }

    private boolean validateWeekly(EventTemplateDto eventTemplateDto, ConstraintValidatorContext context) {

        boolean atLeastOneDaySelected = EventTemplateType.WEEKLY == (eventTemplateDto.getType()) && eventTemplateDto.getDayOfWeek() == null;

        if (atLeastOneDaySelected) {

            context.buildConstraintViolationWithTemplate("При указании типа события - Еженедельно, день недели должен быть задан")
                    .addConstraintViolation();

            atLeastOneDaySelected = false;
        }

        return atLeastOneDaySelected;
    }

    private static boolean validateMonthly(EventTemplateDto eventTemplateDto, ConstraintValidatorContext context) {

        boolean validDayOfMonth = EventTemplateType.MONTHLY == (eventTemplateDto.getType()) && eventTemplateDto.getDayOfMonth() == null;

        if (validDayOfMonth) {
            context.buildConstraintViolationWithTemplate("При указании типа события - Ежемесячно, день месяца должен быть задан")
                    .addConstraintViolation();

            validDayOfMonth = false;
        }

        return validDayOfMonth;
    }
}
