package br.com.itau.wi3.servicoparcela.integration.repository;

import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Teste de integração do ParcelaRepository com banco H2 em memória")
class ParcelaRepositoryIntegrationTest {

    @Autowired
    private ParcelaRepository parcelaRepository;

    @Test
    @DisplayName("Deve salvar uma parcela e recuperá-la pelo id composto")
    void deveSalvarERecuperarParcelaPeloIdComposto() {
        final ParcelaEntity parcela = parcelaEntity((short) 1, 456L, 123L, 789);

        parcelaRepository.saveAndFlush(parcela);

        final Optional<ParcelaEntity> encontrada =
                parcelaRepository.findById(parcelaId((short) 1, 456L, 123L, 789));

        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getId().getNumeroParcela()).isEqualTo((short) 1);
        assertThat(encontrada.get().getId().getNumeroContrato()).isEqualTo(456L);
        assertThat(encontrada.get().getId().getNumeroAcordo()).isEqualTo(123L);
        assertThat(encontrada.get().getId().getCodigoProdutoOperacional()).isEqualTo(789);
        assertThat(encontrada.get().getValorDescontoParcela()).isEqualByComparingTo("10.00");
        assertThat(encontrada.get().getValorBrutoParcela()).isEqualByComparingTo("100.00");
        assertThat(encontrada.get().getValorLiquidoParcela()).isEqualByComparingTo("90.00");
    }

    @Test
    @DisplayName("Deve salvar várias parcelas com saveAll")
    void deveSalvarVariasParcelas() {
        final List<ParcelaEntity> parcelas = List.of(
                parcelaEntity((short) 1, 456L, 123L, 789),
                parcelaEntity((short) 2, 456L, 123L, 789),
                parcelaEntity((short) 1, 654L, 123L, 987)
        );

        parcelaRepository.saveAllAndFlush(parcelas);

        assertThat(parcelaRepository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("Deve atualizar a parcela ao salvar novamente com o mesmo id composto")
    void deveAtualizarParcelaComMesmoIdComposto() {
        parcelaRepository.saveAndFlush(parcelaEntity((short) 1, 456L, 123L, 789));

        final ParcelaEntity atualizada = parcelaEntity((short) 1, 456L, 123L, 789);
        atualizada.setValorDescontoParcela(new BigDecimal("99.99"));
        parcelaRepository.saveAndFlush(atualizada);

        final List<ParcelaEntity> todas = parcelaRepository.findAll();
        assertThat(todas).hasSize(1);
        assertThat(todas.get(0).getValorDescontoParcela()).isEqualByComparingTo("99.99");
    }

    @Test
    @DisplayName("Deve diferenciar parcelas que variam em apenas um campo do id composto")
    void deveDiferenciarParcelasPorCadaCampoDoIdComposto() {
        parcelaRepository.saveAllAndFlush(List.of(
                parcelaEntity((short) 1, 456L, 123L, 789),
                parcelaEntity((short) 2, 456L, 123L, 789),
                parcelaEntity((short) 1, 999L, 123L, 789),
                parcelaEntity((short) 1, 456L, 999L, 789),
                parcelaEntity((short) 1, 456L, 123L, 999)
        ));

        assertThat(parcelaRepository.findAll()).hasSize(5);
        assertThat(parcelaRepository.findById(parcelaId((short) 1, 456L, 999L, 789))).isPresent();
        assertThat(parcelaRepository.findById(parcelaId((short) 9, 456L, 123L, 789))).isEmpty();
    }

    private ParcelaEntity parcelaEntity(
            final Short numeroParcela,
            final Long numeroContrato,
            final Long numeroAcordo,
            final Integer codigoProdutoOperacional
    ) {
        final ParcelaEntity parcelaEntity = new ParcelaEntity();
        parcelaEntity.setId(parcelaId(numeroParcela, numeroContrato, numeroAcordo, codigoProdutoOperacional));
        parcelaEntity.setValorDescontoParcela(new BigDecimal("10.00"));
        parcelaEntity.setValorBrutoParcela(new BigDecimal("100.00"));
        parcelaEntity.setValorLiquidoParcela(new BigDecimal("90.00"));
        return parcelaEntity;
    }

    private ParcelaId parcelaId(
            final Short numeroParcela,
            final Long numeroContrato,
            final Long numeroAcordo,
            final Integer codigoProdutoOperacional
    ) {
        final ParcelaId parcelaId = new ParcelaId();
        parcelaId.setNumeroParcela(numeroParcela);
        parcelaId.setNumeroContrato(numeroContrato);
        parcelaId.setNumeroAcordo(numeroAcordo);
        parcelaId.setCodigoProdutoOperacional(codigoProdutoOperacional);
        return parcelaId;
    }
}
