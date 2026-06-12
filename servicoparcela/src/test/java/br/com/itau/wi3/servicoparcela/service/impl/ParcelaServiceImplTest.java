package br.com.itau.wi3.servicoparcela.service.impl;

import br.com.itau.wi3.servicoparcela.integration.repository.ParcelaRepository;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import br.com.itau.wi3.servicoparcela.service.mapper.ParcelaServiceMapper;
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
class ParcelaServiceImplTest {

    @Mock
    private ParcelaRepository parcelaRepository;

    @Mock
    private ParcelaServiceMapper parcelaServiceMapper;

    @InjectMocks
    private ParcelaServiceImpl parcelaService;

    @Test
    @DisplayName("Deve mapear os dtos para entidades e salvar no repositório")
    void deveMapearESalvarParcelas() {
        final List<ParcelaServiceDto> parcelaServiceDtos = JsonFixture.as(
                "/fixtures/parcela-service-dtos.json",
                new TypeReference<List<ParcelaServiceDto>>() {}
        );
        final List<ParcelaEntity> parcelaEntities = JsonFixture.as(
                "/fixtures/parcela-entities.json",
                new TypeReference<List<ParcelaEntity>>() {}
        );

        when(parcelaServiceMapper.toParcelaEntities(parcelaServiceDtos)).thenReturn(parcelaEntities);

        parcelaService.registrarParcelas(parcelaServiceDtos);

        final InOrder inOrder = inOrder(parcelaServiceMapper, parcelaRepository);
        inOrder.verify(parcelaServiceMapper).toParcelaEntities(parcelaServiceDtos);
        inOrder.verify(parcelaRepository).saveAll(parcelaEntities);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @DisplayName("Deve salvar lista vazia quando não houver parcelas")
    void deveSalvarListaVaziaQuandoNaoHouverParcelas() {
        final List<ParcelaServiceDto> parcelaServiceDtos = List.of();

        when(parcelaServiceMapper.toParcelaEntities(parcelaServiceDtos)).thenReturn(List.of());

        parcelaService.registrarParcelas(parcelaServiceDtos);

        final InOrder inOrder = inOrder(parcelaServiceMapper, parcelaRepository);
        inOrder.verify(parcelaServiceMapper).toParcelaEntities(parcelaServiceDtos);
        inOrder.verify(parcelaRepository).saveAll(List.of());
        inOrder.verifyNoMoreInteractions();
    }
}
