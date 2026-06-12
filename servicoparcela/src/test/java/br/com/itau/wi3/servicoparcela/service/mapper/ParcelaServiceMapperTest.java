package br.com.itau.wi3.servicoparcela.service.mapper;

import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParcelaServiceMapperTest {

    private final ParcelaServiceMapper mapper = Mappers.getMapper(ParcelaServiceMapper.class);

    @Test
    @DisplayName("Deve mapear ParcelaServiceDto para ParcelaEntity com o id composto preenchido")
    void deveMapearParcelaServiceDtoParaEntity() {
        final ParcelaServiceDto parcelaServiceDto = new ParcelaServiceDto(
                (short) 1,
                456L,
                123L,
                789,
                new BigDecimal("10.00"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        );

        final ParcelaEntity parcelaEntity = mapper.toParcelaEntity(parcelaServiceDto);

        assertThat(parcelaEntity).isNotNull();
        assertThat(parcelaEntity.getId()).isNotNull();
        assertThat(parcelaEntity.getId().getNumeroParcela()).isEqualTo((short) 1);
        assertThat(parcelaEntity.getId().getNumeroContrato()).isEqualTo(456L);
        assertThat(parcelaEntity.getId().getNumeroAcordo()).isEqualTo(123L);
        assertThat(parcelaEntity.getId().getCodigoProdutoOperacional()).isEqualTo(789);
        assertThat(parcelaEntity.getValorDescontoParcela()).isEqualByComparingTo("10.00");
        assertThat(parcelaEntity.getValorBrutoParcela()).isEqualByComparingTo("100.00");
        assertThat(parcelaEntity.getValorLiquidoParcela()).isEqualByComparingTo("90.00");
    }

    @Test
    @DisplayName("Deve mapear lista de ParcelaServiceDto para lista de ParcelaEntity")
    void deveMapearListaDeParcelaServiceDtos() {
        final ParcelaServiceDto primeira = new ParcelaServiceDto(
                (short) 1,
                456L,
                123L,
                789,
                new BigDecimal("10.00"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        );
        final ParcelaServiceDto segunda = new ParcelaServiceDto(
                (short) 2,
                654L,
                321L,
                987,
                new BigDecimal("20.00"),
                new BigDecimal("200.00"),
                new BigDecimal("180.00")
        );

        final List<ParcelaEntity> parcelaEntities = mapper.toParcelaEntities(List.of(primeira, segunda));

        assertThat(parcelaEntities).hasSize(2);
        assertThat(parcelaEntities.get(0).getId().getNumeroParcela()).isEqualTo((short) 1);
        assertThat(parcelaEntities.get(0).getId().getNumeroContrato()).isEqualTo(456L);
        assertThat(parcelaEntities.get(1).getId().getNumeroParcela()).isEqualTo((short) 2);
        assertThat(parcelaEntities.get(1).getId().getNumeroAcordo()).isEqualTo(321L);
        assertThat(parcelaEntities.get(1).getValorLiquidoParcela()).isEqualByComparingTo("180.00");
    }

    @Test
    @DisplayName("Deve retornar lista vazia ao mapear lista vazia")
    void deveMapearListaVazia() {
        assertThat(mapper.toParcelaEntities(List.of())).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar null ao mapear dto null")
    void deveRetornarNullQuandoDtoForNull() {
        assertThat(mapper.toParcelaEntity(null)).isNull();
        assertThat(mapper.toParcelaEntities(null)).isNull();
    }
}
