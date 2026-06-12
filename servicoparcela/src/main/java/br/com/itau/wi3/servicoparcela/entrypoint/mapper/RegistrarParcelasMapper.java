package br.com.itau.wi3.servicoparcela.entrypoint.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ContratoRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ParcelaRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrarParcelasMapper {

    RegistrarParcelasCoreDto toRegistrarParcelasCoreDto(final RegistrarParcelasRequest registrarParcelasRequest);

    ContratoCoreDto toContratoCoreDto(final ContratoRequest contratoRequest);

    ParcelaCoreDto toParcelaCoreDto(final ParcelaRequest parcelaRequest);
}
