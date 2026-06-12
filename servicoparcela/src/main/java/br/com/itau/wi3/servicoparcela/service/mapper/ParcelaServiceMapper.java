package br.com.itau.wi3.servicoparcela.service.mapper;

import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParcelaServiceMapper {

    @Mapping(target = "id.numeroContrato", source = "numeroContrato")
    @Mapping(target = "id.numeroParcela", source = "numeroParcela")
    ParcelaEntity toEntity(ParcelaServiceDto parcelaServiceDto);

    List<ParcelaEntity> toEntityList(List<ParcelaServiceDto> parcelaServiceDtoList);
}
