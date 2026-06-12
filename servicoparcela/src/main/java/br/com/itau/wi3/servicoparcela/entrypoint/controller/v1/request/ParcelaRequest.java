package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ParcelaRequest(

        @NotNull(message = "Número da parcela é obrigatório")
        Integer numeroParcela,

        @NotNull(message = "Valor da parcela é obrigatório")
        @Positive(message = "Valor da parcela deve ser positivo")
        BigDecimal valorParcela,

        @NotNull(message = "Data de vencimento é obrigatória")
        LocalDate dataVencimento
) {}
