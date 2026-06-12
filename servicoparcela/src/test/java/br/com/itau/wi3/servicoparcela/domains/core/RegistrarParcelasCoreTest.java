package br.com.itau.wi3.servicoparcela.domains.core;

import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.domains.mapper.RegistrarParcelasCoreMapper;
import br.com.itau.wi3.servicoparcela.service.ParcelaService;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import br.com.itau.wi3.servicoparcela.support.JsonFixture;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegistrarParcelasCoreTest {

    @Mock
    private ParcelaService parcelaService;

    @Mock
    private RegistrarParcelasCoreMapper registrarParcelasCoreMapper;

    @InjectMocks
    private RegistrarParcelasCore registrarParcelasCore;

    @Test
    @DisplayName("Deve mapear o core dto e registrar as parcelas no serviço")
    void deveMapearERegistrarParcelas() {
        final RegistrarParcelasCoreDto registrarParcelasCoreDto = JsonFixture.as(
                "/fixtures/registrar-parcelas-core-dto.json",
                RegistrarParcelasCoreDto.class
        );
        final List<ParcelaServiceDto> parcelaServiceDtos = JsonFixture.as(
                "/fixtures/parcela-service-dtos.json",
                new TypeReference<List<ParcelaServiceDto>>() {}
        );

        when(registrarParcelasCoreMapper.toParcelaServiceDtos(registrarParcelasCoreDto))
                .thenReturn(parcelaServiceDtos);

        registrarParcelasCore.executar(registrarParcelasCoreDto);

        final InOrder inOrder = inOrder(registrarParcelasCoreMapper, parcelaService);
        inOrder.verify(registrarParcelasCoreMapper).toParcelaServiceDtos(registrarParcelasCoreDto);
        inOrder.verify(parcelaService).registrarParcelas(parcelaServiceDtos);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Deve registrar lista vazia quando o mapeamento não produzir parcelas")
    void deveRegistrarListaVaziaQuandoNaoHouverParcelas() {
        final RegistrarParcelasCoreDto registrarParcelasCoreDto =
                new RegistrarParcelasCoreDto(123L, List.of());

        when(registrarParcelasCoreMapper.toParcelaServiceDtos(registrarParcelasCoreDto))
                .thenReturn(List.of());

        registrarParcelasCore.executar(registrarParcelasCoreDto);

        final InOrder inOrder = inOrder(registrarParcelasCoreMapper, parcelaService);
        inOrder.verify(registrarParcelasCoreMapper).toParcelaServiceDtos(registrarParcelasCoreDto);
        inOrder.verify(parcelaService).registrarParcelas(List.of());
        inOrder.verifyNoMoreInteractions();
    }
}
