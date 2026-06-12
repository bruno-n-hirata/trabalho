package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1;

import br.com.itau.wi3.servicoparcela.domains.core.RegistrarParcelasCore;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.mapper.RegistrarParcelasMapper;
import br.com.itau.wi3.servicoparcela.support.JsonFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ParcelaControllerTest {

    @Mock
    private RegistrarParcelasCore registrarParcelasCore;

    @Mock
    private RegistrarParcelasMapper registrarParcelasMapper;

    @InjectMocks
    private ParcelaController parcelaController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(parcelaController)
                .setValidator(validator)
                .build();
    }

    @Test
    @DisplayName("Deve mapear a requisição, executar o core e retornar 201 Created")
    void deveRegistrarParcelasComSucesso() {
        final RegistrarParcelasRequest request = JsonFixture.as(
                "/fixtures/registrar-parcelas-request.json",
                RegistrarParcelasRequest.class
        );
        final RegistrarParcelasCoreDto coreDto = JsonFixture.as(
                "/fixtures/registrar-parcelas-core-dto.json",
                RegistrarParcelasCoreDto.class
        );

        when(registrarParcelasMapper.toRegistrarParcelasCoreDto(request)).thenReturn(coreDto);

        final ResponseEntity<Void> response = parcelaController.registrar(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
        verify(registrarParcelasMapper).toRegistrarParcelasCoreDto(request);
        verify(registrarParcelasCore).executar(coreDto);
    }

    @Test
    @DisplayName("POST /v1/parcelas deve retornar 201 para requisição válida")
    void deveRetornar201ParaRequisicaoValida() throws Exception {
        when(registrarParcelasMapper.toRegistrarParcelasCoreDto(any(RegistrarParcelasRequest.class)))
                .thenReturn(JsonFixture.as(
                        "/fixtures/registrar-parcelas-core-dto.json",
                        RegistrarParcelasCoreDto.class
                ));

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonFixture.asString("/fixtures/registrar-parcelas-request.json")))
                .andExpect(status().isCreated());

        verify(registrarParcelasCore).executar(any(RegistrarParcelasCoreDto.class));
    }

    @ParameterizedTest(name = "POST /v1/parcelas deve retornar 400 para {0}")
    @ValueSource(strings = {
            "/fixtures/registrar-parcelas-request-numero-acordo-nulo.json",
            "/fixtures/registrar-parcelas-request-contratos-vazio.json",
            "/fixtures/registrar-parcelas-request-contrato-sem-parcelas.json",
            "/fixtures/registrar-parcelas-request-numero-parcela-nulo.json",
            "/fixtures/registrar-parcelas-request-valor-tres-decimais.json"
    })
    @DisplayName("POST /v1/parcelas deve retornar 400 para requisições inválidas")
    void deveRetornar400ParaRequisicaoInvalida(final String fixture) throws Exception {
        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonFixture.asString(fixture)))
                .andExpect(status().isBadRequest());

        verify(registrarParcelasCore, never()).executar(any());
    }
}
