package br.com.itau.wi3.servicoparcela.domains.core;

import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.domains.mapper.RegistrarParcelasCoreMapper;
import br.com.itau.wi3.servicoparcela.service.ParcelaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrarParcelasCore {

    private final ParcelaService parcelaService;
    private final RegistrarParcelasCoreMapper registrarParcelasCoreMapper;

    public void executar(final RegistrarParcelasCoreDto registrarParcelasCoreDto) {
        final var parcelas = registrarParcelasCoreMapper.fromContratos(
                registrarParcelasCoreDto.numeroAcordo(),
                registrarParcelasCoreDto.contratos()
        );
        parcelaService.registrarParcelas(parcelas);
    }
}
