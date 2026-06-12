package br.com.itau.wi3.servicoparcela.service.mapper;

import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ParcelaServiceMapper {

    List<ParcelaEntity> toParcelaEntities(final List<ParcelaServiceDto> parcelaServiceDtos);

    @Mapping(target = "id.numeroParcela", source = "numeroParcela")
    @Mapping(target = "id.numeroContrato", source = "numeroContrato")
    @Mapping(target = "id.numeroAcordo", source = "numeroAcordo")
    @Mapping(target = "id.codigoProdutoOperacional", source = "codigoProdutoOperacional")
    ParcelaEntity toParcelaEntity(final ParcelaServiceDto parcelaServiceDto);
}
