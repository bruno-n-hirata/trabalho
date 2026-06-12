package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegistrarParcelasRequest(

        @NotNull(message = "Número do acordo é obrigatório")
        Long numeroAcordo,

        @NotEmpty(message = "A lista de contratos não pode ser vazia")
        @Valid
        List<ContratoRequest> contratos
) {}
