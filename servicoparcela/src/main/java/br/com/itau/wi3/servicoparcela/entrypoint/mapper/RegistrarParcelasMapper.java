package br.com.itau.wi3.servicoparcela.entrypoint.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrarParcelasMapper {

    RegistrarParcelasCoreDto toRegistrarParcelasCoreDto(RegistrarParcelasRequest registrarParcelasRequest);
}
