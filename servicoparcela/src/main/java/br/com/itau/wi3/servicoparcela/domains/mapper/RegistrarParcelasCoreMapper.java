package br.com.itau.wi3.servicoparcela.domains.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrarParcelasCoreMapper {

    default List<ParcelaServiceDto> fromContratos(final Long numeroAcordo, final List<ContratoCoreDto> contratos) {
        return contratos.stream()
                .flatMap(contrato -> contrato.parcelas().stream()
                        .map(parcela -> toParcelaServiceDto(numeroAcordo, contrato, parcela)))
                .toList();
    }

    default ParcelaServiceDto toParcelaServiceDto(final Long numeroAcordo, final ContratoCoreDto contrato, final ParcelaCoreDto parcela) {
        return new ParcelaServiceDto(
                parcela.numeroParcela(),
                contrato.numeroContrato(),
                numeroAcordo,
                contrato.codigoProdutoOperacional(),
                parcela.valorDescontoParcela(),
                parcela.valorBrutoParcela(),
                parcela.valorLiquidoParcela()
        );
    }
}
