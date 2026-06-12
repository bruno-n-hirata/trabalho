package br.com.itau.wi3.servicoparcela.service.dto;

import java.math.BigDecimal;

public record ParcelaServiceDto(
        Short numeroParcela,
        Long numeroContrato,
        Long numeroAcordo,
        Integer codigoProdutoOperacional,
        BigDecimal valorDescontoParcela,
        BigDecimal valorBrutoParcela,
        BigDecimal valorLiquidoParcela
) {}
