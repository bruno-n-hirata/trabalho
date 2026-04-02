package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OitoPrimeirosDigitosZeroNaoPodeValidator
        implements ConstraintValidator<OitoPrimeirosDigitosZeroNaoPode, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() < 8) {
            return true;
        }
        return !value.substring(0, 8).matches("0{8}");
    }
}
