package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ParcelaRequest(

        @NotNull(message = "Número da parcela é obrigatório")
        Short numeroParcela,

        @NotNull(message = "Valor de desconto da parcela é obrigatório")
        @Positive(message = "Valor de desconto deve ser positivo")
        BigDecimal valorDescontoParcela,

        @NotNull(message = "Valor bruto da parcela é obrigatório")
        @Positive(message = "Valor bruto deve ser positivo")
        BigDecimal valorBrutoParcela,

        @NotNull(message = "Valor líquido da parcela é obrigatório")
        @Positive(message = "Valor líquido deve ser positivo")
        BigDecimal valorLiquidoParcela
) {}
