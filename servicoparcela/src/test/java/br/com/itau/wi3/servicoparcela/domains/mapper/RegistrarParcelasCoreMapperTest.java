package br.com.itau.wi3.servicoparcela.domains.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import br.com.itau.wi3.servicoparcela.support.JsonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrarParcelasCoreMapperTest {

    private final RegistrarParcelasCoreMapper mapper = Mappers.getMapper(RegistrarParcelasCoreMapper.class);

    private final RegistrarParcelasCoreDto registrarParcelasCoreDto = JsonFixture.as(
            "/fixtures/registrar-parcelas-core-dto.json",
            RegistrarParcelasCoreDto.class
    );

    @Test
    @DisplayName("Deve achatar contratos e parcelas do acordo em uma lista de ParcelaServiceDto")
    void deveAchatarContratosEParcelas() {
        final List<ParcelaServiceDto> parcelaServiceDtos =
                mapper.toParcelaServiceDtos(registrarParcelasCoreDto);

        assertThat(parcelaServiceDtos).hasSize(3);

        final ParcelaServiceDto primeira = parcelaServiceDtos.get(0);
        assertThat(primeira.numeroAcordo()).isEqualTo(123L);
        assertThat(primeira.numeroContrato()).isEqualTo(456L);
        assertThat(primeira.codigoProdutoOperacional()).isEqualTo(789);
        assertThat(primeira.numeroParcela()).isEqualTo((short) 1);
        assertThat(primeira.valorDescontoParcela()).isEqualByComparingTo("10.00");
        assertThat(primeira.valorBrutoParcela()).isEqualByComparingTo("100.00");
        assertThat(primeira.valorLiquidoParcela()).isEqualByComparingTo("90.00");

        final ParcelaServiceDto segunda = parcelaServiceDtos.get(1);
        assertThat(segunda.numeroAcordo()).isEqualTo(123L);
        assertThat(segunda.numeroContrato()).isEqualTo(456L);
        assertThat(segunda.numeroParcela()).isEqualTo((short) 2);

        final ParcelaServiceDto terceira = parcelaServiceDtos.get(2);
        assertThat(terceira.numeroAcordo()).isEqualTo(123L);
        assertThat(terceira.numeroContrato()).isEqualTo(654L);
        assertThat(terceira.codigoProdutoOperacional()).isEqualTo(987);
        assertThat(terceira.numeroParcela()).isEqualTo((short) 1);
        assertThat(terceira.valorDescontoParcela()).isEqualByComparingTo("30.00");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando o acordo não possuir contratos")
    void deveRetornarListaVaziaQuandoNaoHouverContratos() {
        final RegistrarParcelasCoreDto semContratos = new RegistrarParcelasCoreDto(123L, List.of());

        assertThat(mapper.toParcelaServiceDtos(semContratos)).isEmpty();
    }

    @Test
    @DisplayName("Deve mapear acordo, contrato e parcela para ParcelaServiceDto")
    void deveMapearParcelaServiceDto() {
        final ContratoCoreDto contrato = registrarParcelasCoreDto.contratos().get(1);
        final ParcelaCoreDto parcela = contrato.parcelas().get(0);

        final ParcelaServiceDto parcelaServiceDto = mapper.toParcelaServiceDto(
                registrarParcelasCoreDto.numeroAcordo(),
                contrato,
                parcela
        );

        assertThat(parcelaServiceDto).isNotNull();
        assertThat(parcelaServiceDto.numeroAcordo()).isEqualTo(123L);
        assertThat(parcelaServiceDto.numeroContrato()).isEqualTo(654L);
        assertThat(parcelaServiceDto.codigoProdutoOperacional()).isEqualTo(987);
        assertThat(parcelaServiceDto.numeroParcela()).isEqualTo((short) 1);
        assertThat(parcelaServiceDto.valorDescontoParcela()).isEqualByComparingTo("30.00");
        assertThat(parcelaServiceDto.valorBrutoParcela()).isEqualByComparingTo("300.00");
        assertThat(parcelaServiceDto.valorLiquidoParcela()).isEqualByComparingTo("270.00");
    }
}
