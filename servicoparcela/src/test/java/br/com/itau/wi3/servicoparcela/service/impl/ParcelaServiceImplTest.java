package br.com.itau.wi3.servicoparcela.service.impl;

import br.com.itau.wi3.servicoparcela.integration.repository.ParcelaRepository;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaId;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import br.com.itau.wi3.servicoparcela.service.mapper.ParcelaServiceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
        final List<ParcelaServiceDto> parcelaServiceDtos = List.of(new ParcelaServiceDto(
                (short) 1,
                456L,
                123L,
                789,
                new BigDecimal("10.00"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        ));

        final List<ParcelaEntity> parcelaEntities = List.of(parcelaEntity());

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

    private ParcelaEntity parcelaEntity() {
        final ParcelaId parcelaId = new ParcelaId();
        parcelaId.setNumeroParcela((short) 1);
        parcelaId.setNumeroContrato(456L);
        parcelaId.setNumeroAcordo(123L);
        parcelaId.setCodigoProdutoOperacional(789);

        final ParcelaEntity parcelaEntity = new ParcelaEntity();
        parcelaEntity.setId(parcelaId);
        parcelaEntity.setValorDescontoParcela(new BigDecimal("10.00"));
        parcelaEntity.setValorBrutoParcela(new BigDecimal("100.00"));
        parcelaEntity.setValorLiquidoParcela(new BigDecimal("90.00"));
        return parcelaEntity;
    }
}
