package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation;

import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.JurosBoletoRequest;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.enums.TipoJurosEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Set;

public class ValidarJurosBoletoValidator implements ConstraintValidator<ValidarJurosBoleto, JurosBoletoRequest> {

    private static final LocalDate DATA_MINIMA = LocalDate.of(1960, 1, 1);

    private static final Set<TipoJurosEnum> CODIGOS_VALOR_FIXO = Set.of(
            TipoJurosEnum.VALOR_POR_DIA,
            TipoJurosEnum.VALOR_FIXO
    );

    private static final Set<TipoJurosEnum> CODIGOS_PERCENTUAL = Set.of(
            TipoJurosEnum.PERCENTUAL_POR_DIA,
            TipoJurosEnum.PERCENTUAL_MENSAL
    );

    private static final Set<TipoJurosEnum> CODIGOS_ISENTO = Set.of(
            TipoJurosEnum.ISENTO,
            TipoJurosEnum.ISENTO_2
    );

    @Override
    public boolean isValid(JurosBoletoRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        TipoJurosEnum codigoTipo = request.getCodigoTipoTaxaJuro();
        if (codigoTipo == null) {
            return true;
        }

        boolean valido = true;
        context.disableDefaultConstraintViolation();

        // Validacao dataCalculoTaxaJuro:
        // Deve ser posterior a 01/01/1960.
        // Deve ser NULO quando codigo isento.
        if (CODIGOS_ISENTO.contains(codigoTipo)) {
            if (request.getDataCalculoTaxaJuro() != null) {
                context.buildConstraintViolationWithTemplate(
                        "dataCalculoTaxaJuro deve ser nulo quando o tipo de juros e isento"
                ).addPropertyNode("dataCalculoTaxaJuro").addConstraintViolation();
                valido = false;
            }
        } else {
            if (request.getDataCalculoTaxaJuro() != null && !request.getDataCalculoTaxaJuro().isAfter(DATA_MINIMA)) {
                context.buildConstraintViolationWithTemplate(
                        "dataCalculoTaxaJuro deve ser posterior a 01/01/1960"
                ).addPropertyNode("dataCalculoTaxaJuro").addConstraintViolation();
                valido = false;
            }
        }

        // Validacao valorJuro:
        // Obrigatorio para codigos 1 e 6 (valor fixo).
        // Mutuamente exclusivo com percentualJuro.
        // Quando isento, deve ser nulo.
        if (CODIGOS_VALOR_FIXO.contains(codigoTipo)) {
            if (request.getValorJuro() == null) {
                context.buildConstraintViolationWithTemplate(
                        "valorJuro e obrigatorio para codigos de valor fixo (1 e 6)"
                ).addPropertyNode("valorJuro").addConstraintViolation();
                valido = false;
            }
            if (request.getPercentualJuro() != null) {
                context.buildConstraintViolationWithTemplate(
                        "percentualJuro deve ser nulo quando o tipo de juros e valor fixo (codigos 1 e 6)"
                ).addPropertyNode("percentualJuro").addConstraintViolation();
                valido = false;
            }
        }

        // Validacao percentualJuro:
        // Obrigatorio para codigos 3 e 8 (percentual).
        // Mutuamente exclusivo com valorJuro.
        // Quando isento, deve ser nulo.
        if (CODIGOS_PERCENTUAL.contains(codigoTipo)) {
            if (request.getPercentualJuro() == null) {
                context.buildConstraintViolationWithTemplate(
                        "percentualJuro e obrigatorio para codigos percentuais (3 e 8)"
                ).addPropertyNode("percentualJuro").addConstraintViolation();
                valido = false;
            }
            if (request.getValorJuro() != null) {
                context.buildConstraintViolationWithTemplate(
                        "valorJuro deve ser nulo quando o tipo de juros e percentual (codigos 3 e 8)"
                ).addPropertyNode("valorJuro").addConstraintViolation();
                valido = false;
            }
        }

        // Quando isento, valorJuro e percentualJuro devem ser nulos
        if (CODIGOS_ISENTO.contains(codigoTipo)) {
            if (request.getValorJuro() != null) {
                context.buildConstraintViolationWithTemplate(
                        "valorJuro deve ser nulo quando o tipo de juros e isento"
                ).addPropertyNode("valorJuro").addConstraintViolation();
                valido = false;
            }
            if (request.getPercentualJuro() != null) {
                context.buildConstraintViolationWithTemplate(
                        "percentualJuro deve ser nulo quando o tipo de juros e isento"
                ).addPropertyNode("percentualJuro").addConstraintViolation();
                valido = false;
            }
        }

        return valido;
    }
}
