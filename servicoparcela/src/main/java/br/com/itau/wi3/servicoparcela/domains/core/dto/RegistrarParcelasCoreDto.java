package br.com.itau.wi3.servicoparcela.domains.core.dto;

import java.util.List;

public record RegistrarParcelasCoreDto(
        Long numeroAcordo,
        List<ContratoCoreDto> contratos
) {}
