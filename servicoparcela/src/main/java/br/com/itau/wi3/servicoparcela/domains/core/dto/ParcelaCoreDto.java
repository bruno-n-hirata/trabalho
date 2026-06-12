package br.com.itau.wi3.servicoparcela.domains.core.dto;

import java.math.BigDecimal;

public record ParcelaCoreDto(
        Short numeroParcela,
        BigDecimal valorDescontoParcela,
        BigDecimal valorBrutoParcela,
        BigDecimal valorLiquidoParcela
) {}
