package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation;

import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.SolicitarInclusaoBoletoDataRequest;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.enums.SimNaoEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidarSolicitarInclusaoBoletoDataValidator
        implements ConstraintValidator<ValidarSolicitarInclusaoBoletoData, SolicitarInclusaoBoletoDataRequest> {

    @Override
    public boolean isValid(SolicitarInclusaoBoletoDataRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        boolean valido = true;
        context.disableDefaultConstraintViolation();

        // Validacao: quantidadePagamentoParcial e obrigatorio quando indicadorAceitePagamentoParcial = 'S'
        if (SimNaoEnum.S.equals(request.getIndicadorAceitePagamentoParcial())) {
            if (request.getInstrucaoPagamento() == null
                    || request.getInstrucaoPagamento().getQuantidadePagamentoParcial() == null
                    || request.getInstrucaoPagamento().getQuantidadePagamentoParcial().isBlank()) {
                context.buildConstraintViolationWithTemplate(
                        "quantidadePagamentoParcial e obrigatorio quando indicadorAceitePagamentoParcial e 'S'"
                ).addPropertyNode("instrucaoPagamento")
                 .addPropertyNode("quantidadePagamentoParcial")
                 .addConstraintViolation();
                valido = false;
            }
        }

        return valido;
    }
}
