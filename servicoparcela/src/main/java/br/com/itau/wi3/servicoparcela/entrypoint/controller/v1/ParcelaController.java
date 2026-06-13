package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1;

import br.com.itau.wi3.servicoparcela.domains.core.RegistrarParcelasCore;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.mapper.RegistrarParcelasMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/parcelas")
@RequiredArgsConstructor
public class ParcelaController {

    private final RegistrarParcelasCore registrarParcelasCore;
    private final RegistrarParcelasMapper registrarParcelasMapper;

    @PostMapping
    public ResponseEntity<Void> registrar(
            @Valid @RequestBody final RegistrarParcelasRequest registrarParcelasRequest
    ) {
        log.info("Recebida solicitação de registro de parcelas. numeroAcordo={}, quantidadeContratos={}",
                registrarParcelasRequest.numeroAcordo(), registrarParcelasRequest.contratos().size());

        final RegistrarParcelasCoreDto registrarParcelasCoreDto =
                registrarParcelasMapper.toRegistrarParcelasCoreDto(registrarParcelasRequest);

        registrarParcelasCore.executar(registrarParcelasCoreDto);

        log.info("Parcelas registradas com sucesso. numeroAcordo={}", registrarParcelasRequest.numeroAcordo());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
