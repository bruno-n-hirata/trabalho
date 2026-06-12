package br.com.itau.wi3.servicoparcela;

import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ContratoRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ParcelaRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import br.com.itau.wi3.servicoparcela.integration.repository.ParcelaRepository;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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
    private ObjectMapper objectMapper;

    @Autowired
    private ParcelaRepository parcelaRepository;

    @BeforeEach
    void setUp() {
        parcelaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve registrar as parcelas de todos os contratos do acordo e persistir no banco")
    void deveRegistrarParcelasEPersistirNoBanco() throws Exception {
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                123L,
                List.of(
                        new ContratoRequest(456L, 789, List.of(
                                new ParcelaRequest(
                                        (short) 1,
                                        new BigDecimal("10.00"),
                                        new BigDecimal("100.00"),
                                        new BigDecimal("90.00")
                                ),
                                new ParcelaRequest(
                                        (short) 2,
                                        new BigDecimal("20.00"),
                                        new BigDecimal("200.00"),
                                        new BigDecimal("180.00")
                                )
                        )),
                        new ContratoRequest(654L, 987, List.of(
                                new ParcelaRequest(
                                        (short) 1,
                                        new BigDecimal("30.00"),
                                        new BigDecimal("300.00"),
                                        new BigDecimal("270.00")
                                )
                        ))
                )
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

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
        registrarParcelaUnica(new BigDecimal("90.00"));
        registrarParcelaUnica(new BigDecimal("85.00"));

        final List<ParcelaEntity> parcelas = parcelaRepository.findAll();
        assertThat(parcelas).hasSize(1);
        assertThat(parcelas.get(0).getValorLiquidoParcela()).isEqualByComparingTo("85.00");
    }

    @Test
    @DisplayName("Deve retornar 400 e não persistir nada quando a requisição for inválida")
    void deveRetornar400ENaoPersistirQuandoRequisicaoInvalida() throws Exception {
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                null,
                List.of(new ContratoRequest(456L, 789, List.of(
                        new ParcelaRequest(
                                (short) 1,
                                new BigDecimal("10.00"),
                                new BigDecimal("100.00"),
                                new BigDecimal("90.00")
                        )
                )))
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        assertThat(parcelaRepository.findAll()).isEmpty();
    }

    private void registrarParcelaUnica(final BigDecimal valorLiquido) throws Exception {
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                123L,
                List.of(new ContratoRequest(456L, 789, List.of(
                        new ParcelaRequest(
                                (short) 1,
                                new BigDecimal("10.00"),
                                new BigDecimal("100.00"),
                                valorLiquido
                        )
                )))
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
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
