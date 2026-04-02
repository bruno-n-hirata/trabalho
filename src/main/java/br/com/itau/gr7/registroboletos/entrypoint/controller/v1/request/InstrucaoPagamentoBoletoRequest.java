package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request;

import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.ValidarInstrucaoPagamentoBoleto;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.SomenteDigitosNumericos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidarInstrucaoPagamentoBoleto
public class InstrucaoPagamentoBoletoRequest {

    @NotNull
    private LocalDate dataVencimento;

    @NotNull
    @Positive
    @Digits(integer = 15, fraction = 2)
    private BigDecimal valor;

    @NotNull
    private LocalDate dataLimitePagamentoTitulo;

    @Size(max = 2)
    @SomenteDigitosNumericos
    private String quantidadePagamentoParcial;

    @NotNull
    @PositiveOrZero
    @Digits(integer = 15, fraction = 2)
    private BigDecimal valorAbatimento;
}
