package br.com.itau.wi3.servicoparcela.domains.core.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaCoreDto(
        Integer numeroParcela,
        BigDecimal valorParcela,
        LocalDate dataVencimento
) {}
