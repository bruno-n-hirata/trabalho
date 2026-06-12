package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ParcelaRequest(

        @NotNull(message = "Número da parcela é obrigatório")
        Short numeroParcela,

        @NotNull(message = "Valor desconto da parcela é obrigatório")
        @Digits(integer = 13, fraction = 2, message = "Valor desconto da parcela deve possuir no máximo 13 inteiros e 2 decimais")
        BigDecimal valorDescontoParcela,

        @NotNull(message = "Valor bruto da parcela é obrigatório")
        @Digits(integer = 13, fraction = 2, message = "Valor bruto da parcela deve possuir no máximo 13 inteiros e 2 decimais")
        BigDecimal valorBrutoParcela,

        @NotNull(message = "Valor líquido da parcela é obrigatório")
        @Digits(integer = 13, fraction = 2, message = "Valor líquido da parcela deve possuir no máximo 13 inteiros e 2 decimais")
        BigDecimal valorLiquidoParcela
) {}
