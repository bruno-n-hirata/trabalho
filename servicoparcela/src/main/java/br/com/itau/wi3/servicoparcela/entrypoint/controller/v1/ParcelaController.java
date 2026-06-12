package br.com.itau.wi3.servicoparcela.entrypoint.controller.v1;

import br.com.itau.wi3.servicoparcela.domains.core.RegistrarParcelasCore;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.entrypoint.controller.v1.request.RegistrarParcelasRequest;
import br.com.itau.wi3.servicoparcela.entrypoint.mapper.RegistrarParcelasMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        final RegistrarParcelasCoreDto registrarParcelasCoreDto =
                registrarParcelasMapper.toRegistrarParcelasCoreDto(registrarParcelasRequest);

        registrarParcelasCore.executar(registrarParcelasCoreDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
