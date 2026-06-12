package br.com.itau.wi3.servicoparcela.domains.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RegistrarParcelasCoreMapper {

    List<ParcelaServiceDto> toParcelaServiceDtoList(RegistrarParcelasCoreDto registrarParcelasCoreDto);

    default List<ParcelaServiceDto> fromContratos(List<ContratoCoreDto> contratos) {
        return contratos.stream()
                .flatMap(contrato -> contrato.parcelas().stream()
                        .map(parcela -> toParcelaServiceDto(contrato, parcela)))
                .toList();
    }

    default ParcelaServiceDto toParcelaServiceDto(ContratoCoreDto contrato, ParcelaCoreDto parcela) {
        return new ParcelaServiceDto(
                contrato.numeroContrato(),
                contrato.codigoProdutoOperacional(),
                parcela.numeroParcela(),
                parcela.valorParcela(),
                parcela.dataVencimento()
        );
    }
}
