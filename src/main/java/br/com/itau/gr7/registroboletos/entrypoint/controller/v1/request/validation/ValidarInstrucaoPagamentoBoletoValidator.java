package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation;

import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.InstrucaoPagamentoBoletoRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidarInstrucaoPagamentoBoletoValidator
        implements ConstraintValidator<ValidarInstrucaoPagamentoBoleto, InstrucaoPagamentoBoletoRequest> {

    @Override
    public boolean isValid(InstrucaoPagamentoBoletoRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        boolean valido = true;
        context.disableDefaultConstraintViolation();

        // Validacao: dataLimitePagamentoTitulo deve ser igual ou posterior a dataVencimento
        if (request.getDataVencimento() != null && request.getDataLimitePagamentoTitulo() != null) {
            if (request.getDataLimitePagamentoTitulo().isBefore(request.getDataVencimento())) {
                context.buildConstraintViolationWithTemplate(
                        "dataLimitePagamentoTitulo deve ser igual ou posterior a dataVencimento"
                ).addPropertyNode("dataLimitePagamentoTitulo").addConstraintViolation();
                valido = false;
            }
        }

        return valido;
    }
}
