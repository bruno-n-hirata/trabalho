package br.com.itau.wi3.servicoparcela.domains.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrarParcelasCoreMapper {

    default List<ParcelaServiceDto> toParcelaServiceDtos(
            final RegistrarParcelasCoreDto registrarParcelasCoreDto
    ) {
        return registrarParcelasCoreDto.contratos()
                .stream()
                .flatMap((ContratoCoreDto contrato) -> contrato.parcelas()
                        .stream()
                        .map((ParcelaCoreDto parcela) -> toParcelaServiceDto(
                                registrarParcelasCoreDto.numeroAcordo(),
                                contrato,
                                parcela
                        )))
                .toList();
    }

    ParcelaServiceDto toParcelaServiceDto(
            final Long numeroAcordo,
            final ContratoCoreDto contrato,
            final ParcelaCoreDto parcela
    );
}
