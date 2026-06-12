package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1;

import br.com.itau.wi3.servicoparcela.domains.core.RegistrarParcelasCore;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ContratoCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.ParcelaCoreDto;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ContratoRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.ParcelaRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.mapper.RegistrarParcelasMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.math.BigDecimal;
import java.util.List;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

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
        final RegistrarParcelasRequest request = requestValida();
        final RegistrarParcelasCoreDto coreDto = coreDtoValido();

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
                .thenReturn(coreDtoValido());

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValida())))
                .andExpect(status().isCreated());

        verify(registrarParcelasCore).executar(any(RegistrarParcelasCoreDto.class));
    }

    @Test
    @DisplayName("POST /v1/parcelas deve retornar 400 quando número do acordo for nulo")
    void deveRetornar400QuandoNumeroAcordoNulo() throws Exception {
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                null,
                List.of(contratoRequestValido())
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(registrarParcelasCore, never()).executar(any());
    }

    @Test
    @DisplayName("POST /v1/parcelas deve retornar 400 quando lista de contratos for vazia")
    void deveRetornar400QuandoContratosVazio() throws Exception {
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(123L, List.of());

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(registrarParcelasCore, never()).executar(any());
    }

    @Test
    @DisplayName("POST /v1/parcelas deve retornar 400 quando contrato não possuir parcelas")
    void deveRetornar400QuandoContratoSemParcelas() throws Exception {
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                123L,
                List.of(new ContratoRequest(456L, 789, List.of()))
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(registrarParcelasCore, never()).executar(any());
    }

    @Test
    @DisplayName("POST /v1/parcelas deve retornar 400 quando número da parcela for nulo")
    void deveRetornar400QuandoNumeroParcelaNulo() throws Exception {
        final ParcelaRequest parcelaSemNumero = new ParcelaRequest(
                null,
                new BigDecimal("10.00"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        );
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                123L,
                List.of(new ContratoRequest(456L, 789, List.of(parcelaSemNumero)))
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(registrarParcelasCore, never()).executar(any());
    }

    @Test
    @DisplayName("POST /v1/parcelas deve retornar 400 quando valor da parcela exceder 2 casas decimais")
    void deveRetornar400QuandoValorParcelaExcederCasasDecimais() throws Exception {
        final ParcelaRequest parcelaInvalida = new ParcelaRequest(
                (short) 1,
                new BigDecimal("10.001"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        );
        final RegistrarParcelasRequest request = new RegistrarParcelasRequest(
                123L,
                List.of(new ContratoRequest(456L, 789, List.of(parcelaInvalida)))
        );

        mockMvc.perform(post("/v1/parcelas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(registrarParcelasCore, never()).executar(any());
    }

    private RegistrarParcelasRequest requestValida() {
        return new RegistrarParcelasRequest(123L, List.of(contratoRequestValido()));
    }

    private ContratoRequest contratoRequestValido() {
        return new ContratoRequest(456L, 789, List.of(parcelaRequestValida()));
    }

    private ParcelaRequest parcelaRequestValida() {
        return new ParcelaRequest(
                (short) 1,
                new BigDecimal("10.00"),
                new BigDecimal("100.00"),
                new BigDecimal("90.00")
        );
    }

    private RegistrarParcelasCoreDto coreDtoValido() {
        return new RegistrarParcelasCoreDto(
                123L,
                List.of(new ContratoCoreDto(
                        456L,
                        789,
                        List.of(new ParcelaCoreDto(
                                (short) 1,
                                new BigDecimal("10.00"),
                                new BigDecimal("100.00"),
                                new BigDecimal("90.00")
                        ))
                ))
        );
    }
}
