package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ContratoRequest(

        @NotNull(message = "Número do contrato é obrigatório")
        Long numeroContrato,

        @NotNull(message = "Código do produto operacional é obrigatório")
        Integer codigoProdutoOperacional,

        @NotEmpty(message = "Contrato deve possuir ao menos uma parcela")
        @Valid
        List<ParcelaRequest> parcelas
) {}
