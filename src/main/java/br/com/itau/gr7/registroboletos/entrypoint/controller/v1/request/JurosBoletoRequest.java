package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request;

import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.enums.TipoJurosEnum;
import br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.validation.ValidarJurosBoleto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidarJurosBoleto
public class JurosBoletoRequest {

    @NotNull
    private TipoJurosEnum codigoTipoTaxaJuro;

    private LocalDate dataCalculoTaxaJuro;

    @PositiveOrZero
    @Digits(integer = 15, fraction = 2)
    private BigDecimal valorJuro;

    @PositiveOrZero
    @Digits(integer = 12, fraction = 5)
    private BigDecimal percentualJuro;
}
