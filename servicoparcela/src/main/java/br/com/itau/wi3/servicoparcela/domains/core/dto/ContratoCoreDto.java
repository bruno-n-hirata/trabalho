package br.com.itau.wi3.servicoparcela.domains.core.dto;

import java.util.List;

public record ContratoCoreDto(
        Long numeroContrato,
        Integer codigoProdutoOperacional,
        List<ParcelaCoreDto> parcelas
) {}
