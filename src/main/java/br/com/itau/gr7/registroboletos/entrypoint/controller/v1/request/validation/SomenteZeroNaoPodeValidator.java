package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SomenteZeroNaoPodeValidator implements ConstraintValidator<SomenteZeroNaoPode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        return !value.matches("0+");
    }
}
