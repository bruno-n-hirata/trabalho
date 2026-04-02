package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request;

import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.enums.SimNaoEnum;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.OitoPrimeirosDigitosZeroNaoPode;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.SomenteDigitosNumericos;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.SomenteZeroNaoPode;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.ValidarSolicitarInclusaoBoletoData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidarSolicitarInclusaoBoletoData
public class SolicitarInclusaoBoletoDataRequest {

    @NotBlank
    @Size(min = 9, max = 9)
    @SomenteDigitosNumericos
    @OitoPrimeirosDigitosZeroNaoPode
    private String nossoNumero;

    @NotBlank
    @Size(min = 44, max = 44)
    @SomenteDigitosNumericos
    @SomenteZeroNaoPode
    private String numeroCodigoBarras;

    @NotNull
    private SimNaoEnum indicadorNegociado;

    @NotNull
    private SimNaoEnum indicadorAceitaValorAberto;

    @NotNull
    private SimNaoEnum indicadorAceitePagamentoParcial;

    @Valid
    private InstrucaoPagamentoBoletoRequest instrucaoPagamento;

    @Valid
    private JurosBoletoRequest juros;
}
