package br.com.itau.wi3.servicoparcela.entrypoint.mapper;

import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import br.com.itau.wi3.servicoparcela.support.JsonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrarParcelasMapperTest {

    private final RegistrarParcelasMapper mapper = Mappers.getMapper(RegistrarParcelasMapper.class);

    private final RegistrarParcelasRequest request = JsonFixture.as(
            "/fixtures/registrar-parcelas-request.json",
            RegistrarParcelasRequest.class
    );

    @Test
    @DisplayName("Deve mapear RegistrarParcelasRequest para RegistrarParcelasCoreDto com todos os campos")
    void deveMapearRegistrarParcelasRequest() {
        final RegistrarParcelasCoreDto coreDto = mapper.toRegistrarParcelasCoreDto(request);

        assertThat(coreDto).isNotNull();
        assertThat(coreDto.numeroAcordo()).isEqualTo(123L);
        assertThat(coreDto.contratos()).hasSize(2);

        final ContratoCoreDto primeiroContrato = coreDto.contratos().get(0);
        assertThat(primeiroContrato.numeroContrato()).isEqualTo(456L);
        assertThat(primeiroContrato.codigoProdutoOperacional()).isEqualTo(789);
        assertThat(primeiroContrato.parcelas()).hasSize(2);

        final ParcelaCoreDto primeiraParcela = primeiroContrato.parcelas().get(0);
        assertThat(primeiraParcela.numeroParcela()).isEqualTo((short) 1);
        assertThat(primeiraParcela.valorDescontoParcela()).isEqualByComparingTo("10.00");
        assertThat(primeiraParcela.valorBrutoParcela()).isEqualByComparingTo("100.00");
        assertThat(primeiraParcela.valorLiquidoParcela()).isEqualByComparingTo("90.00");

        final ContratoCoreDto segundoContrato = coreDto.contratos().get(1);
        assertThat(segundoContrato.numeroContrato()).isEqualTo(654L);
        assertThat(segundoContrato.codigoProdutoOperacional()).isEqualTo(987);
        assertThat(segundoContrato.parcelas()).hasSize(1);
        assertThat(segundoContrato.parcelas().get(0).valorBrutoParcela()).isEqualByComparingTo("300.00");
    }

    @Test
    @DisplayName("Deve mapear ContratoRequest para ContratoCoreDto")
    void deveMapearContratoRequest() {
        final ContratoCoreDto contratoCoreDto = mapper.toContratoCoreDto(request.contratos().get(0));

        assertThat(contratoCoreDto).isNotNull();
        assertThat(contratoCoreDto.numeroContrato()).isEqualTo(456L);
        assertThat(contratoCoreDto.codigoProdutoOperacional()).isEqualTo(789);
        assertThat(contratoCoreDto.parcelas()).hasSize(2);
        assertThat(contratoCoreDto.parcelas().get(0).numeroParcela()).isEqualTo((short) 1);
        assertThat(contratoCoreDto.parcelas().get(1).numeroParcela()).isEqualTo((short) 2);
    }

    @Test
    @DisplayName("Deve mapear ParcelaRequest para ParcelaCoreDto")
    void deveMapearParcelaRequest() {
        final ParcelaCoreDto parcelaCoreDto =
                mapper.toParcelaCoreDto(request.contratos().get(0).parcelas().get(1));

        assertThat(parcelaCoreDto).isNotNull();
        assertThat(parcelaCoreDto.numeroParcela()).isEqualTo((short) 2);
        assertThat(parcelaCoreDto.valorDescontoParcela()).isEqualByComparingTo("20.00");
        assertThat(parcelaCoreDto.valorBrutoParcela()).isEqualByComparingTo("200.00");
        assertThat(parcelaCoreDto.valorLiquidoParcela()).isEqualByComparingTo("180.00");
    }

    @Test
    @DisplayName("Deve retornar null quando a requisição for null")
    void deveRetornarNullQuandoRequestForNull() {
        assertThat(mapper.toRegistrarParcelasCoreDto(null)).isNull();
        assertThat(mapper.toContratoCoreDto(null)).isNull();
        assertThat(mapper.toParcelaCoreDto(null)).isNull();
    }
}
