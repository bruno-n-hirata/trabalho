package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RegistrarParcelasRequest(

        @NotEmpty(message = "A lista de contratos não pode ser vazia")
        @Valid
        List<ContratoRequest> contratos
) {}
