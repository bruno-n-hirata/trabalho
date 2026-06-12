package br.com.itau.wi3.servicoparcela.entrypoint.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ContratoRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ParcelaRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrarParcelasMapperTest {

    private final RegistrarParcelasMapper mapper = Mappers.getMapper(RegistrarParcelasMapper.class);

    @Test
    @DisplayName("Deve mapear RegistrarParcelasRequest para RegistrarParcelasCoreDto com todos os campos")
    void deveMapearRegistrarParcelasRequest() {
        final ParcelaRequest parcelaRequest = new ParcelaRequest(
                (short) 1,
                new BigDecimal("10.00"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        );
        final ContratoRequest contratoRequest = new ContratoRequest(456L, 789, List.of(parcelaRequest));
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(123L, List.of(contratoRequest));

        final RegistrarParcelasCoreDto coreDto = mapper.toRegistrarParcelasCoreDto(request);

        assertThat(coreDto).isNotNull();
        assertThat(coreDto.numeroAcordo()).isEqualTo(123L);
        assertThat(coreDto.contratos()).hasSize(1);

        final ContratoCoreDto contratoCoreDto = coreDto.contratos().get(0);
        assertThat(contratoCoreDto.numeroContrato()).isEqualTo(456L);
        assertThat(contratoCoreDto.codigoProdutoOperacional()).isEqualTo(789);
        assertThat(contratoCoreDto.parcelas()).hasSize(1);

        final ParcelaCoreDto parcelaCoreDto = contratoCoreDto.parcelas().get(0);
        assertThat(parcelaCoreDto.numeroParcela()).isEqualTo((short) 1);
        assertThat(parcelaCoreDto.valorDescontoParcela()).isEqualByComparingTo("10.00");
        assertThat(parcelaCoreDto.valorBrutoParcela()).isEqualByComparingTo("100.00");
        assertThat(parcelaCoreDto.valorLiquidoParcela()).isEqualByComparingTo("90.00");
    }

    @Test
    @DisplayName("Deve mapear ContratoRequest para ContratoCoreDto")
    void deveMapearContratoRequest() {
        final ParcelaRequest parcelaRequest = new ParcelaRequest(
                (short) 2,
                new BigDecimal("5.50"),
                new BigDecimal("55.50"),
                new BigDecimal("50.00")
        );
        final ContratoRequest contratoRequest = new ContratoRequest(111L, 222, List.of(parcelaRequest));

        final ContratoCoreDto contratoCoreDto = mapper.toContratoCoreDto(contratoRequest);

        assertThat(contratoCoreDto).isNotNull();
        assertThat(contratoCoreDto.numeroContrato()).isEqualTo(111L);
        assertThat(contratoCoreDto.codigoProdutoOperacional()).isEqualTo(222);
        assertThat(contratoCoreDto.parcelas()).hasSize(1);
        assertThat(contratoCoreDto.parcelas().get(0).numeroParcela()).isEqualTo((short) 2);
    }

    @Test
    @DisplayName("Deve mapear ParcelaRequest para ParcelaCoreDto")
    void deveMapearParcelaRequest() {
        final ParcelaRequest parcelaRequest = new ParcelaRequest(
                (short) 3,
                new BigDecimal("1.00"),
                new BigDecimal("20.00"),
                new BigDecimal("19.00")
        );

        final ParcelaCoreDto parcelaCoreDto = mapper.toParcelaCoreDto(parcelaRequest);

        assertThat(parcelaCoreDto).isNotNull();
        assertThat(parcelaCoreDto.numeroParcela()).isEqualTo((short) 3);
        assertThat(parcelaCoreDto.valorDescontoParcela()).isEqualByComparingTo("1.00");
        assertThat(parcelaCoreDto.valorBrutoParcela()).isEqualByComparingTo("20.00");
        assertThat(parcelaCoreDto.valorLiquidoParcela()).isEqualByComparingTo("19.00");
    }

    @Test
    @DisplayName("Deve retornar null quando a requisição for null")
    void deveRetornarNullQuandoRequestForNull() {
        assertThat(mapper.toRegistrarParcelasCoreDto(null)).isNull();
        assertThat(mapper.toContratoCoreDto(null)).isNull();
        assertThat(mapper.toParcelaCoreDto(null)).isNull();
    }
}
