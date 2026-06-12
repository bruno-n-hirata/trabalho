package br.com.itau.wi3.servicoparcela;

import br.com.itau.wi3.servicoparcela.integration.repository.ParcelaRepository;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaId;
import br.com.itau.wi3.servicoparcela.support.JsonFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Teste de integração do fluxo de registro de parcelas (HTTP -> banco H2)")
class RegistrarParcelasIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParcelaRepository parcelaRepository;

    @BeforeEach
    void setUp() {
        parcelaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve registrar as parcelas de todos os contratos do acordo e persistir no banco")
    void deveRegistrarParcelasEPersistirNoBanco() throws Exception {
        registrar("/fixtures/registrar-parcelas-request.json");

        final List<ParcelaEntity> parcelas = parcelaRepository.findAll();
        assertThat(parcelas).hasSize(3);

        final Optional<ParcelaEntity> primeiraParcela =
                parcelaRepository.findById(parcelaId((short) 1, 456L, 123L, 789));
        assertThat(primeiraParcela).isPresent();
        assertThat(primeiraParcela.get().getValorDescontoParcela()).isEqualByComparingTo("10.00");
        assertThat(primeiraParcela.get().getValorBrutoParcela()).isEqualByComparingTo("100.00");
        assertThat(primeiraParcela.get().getValorLiquidoParcela()).isEqualByComparingTo("90.00");

        final Optional<ParcelaEntity> segundaParcela =
                parcelaRepository.findById(parcelaId((short) 2, 456L, 123L, 789));
        assertThat(segundaParcela).isPresent();
        assertThat(segundaParcela.get().getValorLiquidoParcela()).isEqualByComparingTo("180.00");

        final Optional<ParcelaEntity> parcelaSegundoContrato =
                parcelaRepository.findById(parcelaId((short) 1, 654L, 123L, 987));
        assertThat(parcelaSegundoContrato).isPresent();
        assertThat(parcelaSegundoContrato.get().getValorBrutoParcela()).isEqualByComparingTo("300.00");
    }

    @Test
    @DisplayName("Deve atualizar parcela existente ao registrar novamente o mesmo acordo")
    void deveAtualizarParcelaAoRegistrarNovamente() throws Exception {
        registrar("/fixtures/registrar-parcelas-request.json");
        registrar("/fixtures/registrar-parcelas-request-atualizacao.json");

        assertThat(parcelaRepository.findAll()).hasSize(3);

        final Optional<ParcelaEntity> atualizada =
                parcelaRepository.findById(parcelaId((short) 1, 456L, 123L, 789));
        assertThat(atualizada).isPresent();
        assertThat(atualizada.get().getValorDescontoParcela()).isEqualByComparingTo("15.00");
        assertThat(atualizada.get().getValorLiquidoParcela()).isEqualByComparingTo("85.00");
    }

    @Test
    @DisplayName("Deve retornar 400 e não persistir nada quando a requisição for inválida")
    void deveRetornar400ENaoPersistirQuandoRequisicaoInvalida() throws Exception {
        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonFixture.asString(
                                "/fixtures/registrar-parcelas-request-numero-acordo-nulo.json")))
                .andExpect(status().isBadRequest());

        assertThat(parcelaRepository.findAll()).isEmpty();
    }

    private void registrar(final String fixture) throws Exception {
        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonFixture.asString(fixture)))
                .andExpect(status().isCreated());
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
