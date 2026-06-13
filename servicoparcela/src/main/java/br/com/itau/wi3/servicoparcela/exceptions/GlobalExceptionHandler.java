package br.com.itau.wi3.servicoparcela.exceptions;

import br.com.itau.configuration.dynamic.client.ConfigurationService;
import br.com.itau.configuration.dynamic.client.FeatureToggleService;
import br.com.itau.internet.core.web.bff.BffResponse;
import br.com.itau.internet.core.web.bff.Error;
import br.com.itau.quickcloud.microserviceexceptionhandling.MicroserviceDefaultExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends MicroserviceDefaultExceptionHandler {

    private static final String ERRO_INTERNO = "ERRO_INTERNO";

    public GlobalExceptionHandler(
            FeatureToggleService featureToggleService,
            ConfigurationService configService,
            ObjectMapper objectMapper
    ) {
        super(featureToggleService, configService, objectMapper);
    }

    @Override
    @ExceptionHandler(Exception.class)
    public BffResponse<Void> errorException(final Exception exception, final HttpServletResponse response) {
        log.error("Erro inesperado ao processar requisição", exception);
        response.setStatus(INTERNAL_SERVER_ERROR.value());
        return new BffResponse.Builder<Void>()
                .addError(new Error(ERRO_INTERNO, DEFAULT_ERROR_MESSAGE))
                .build();
    }
}
