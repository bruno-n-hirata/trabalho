package br.com.itau.wi3.servicoparcela.service.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaServiceDto(
        Long numeroContrato,
        Integer codigoProdutoOperacional,
        Integer numeroParcela,
        BigDecimal valorParcela,
        LocalDate dataVencimento
) {}
