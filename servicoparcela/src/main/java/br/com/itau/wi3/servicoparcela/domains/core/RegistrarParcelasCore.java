package br.com.itau.wi3.servicoparcela.domains.core;

import br.com.itau.internet.core.annotation.AutoLog;
import br.com.itau.wi3.servicoparcela.domains.core.dto.RegistrarParcelasCoreDto;
import br.com.itau.wi3.servicoparcela.domains.mapper.RegistrarParcelasCoreMapper;
import br.com.itau.wi3.servicoparcela.service.ParcelaService;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AutoLog
@RequiredArgsConstructor
public class RegistrarParcelasCore {

    private final ParcelaService parcelaService;
    private final RegistrarParcelasCoreMapper registrarParcelasCoreMapper;

    public void executar(final RegistrarParcelasCoreDto registrarParcelasCoreDto) {
        final List<ParcelaServiceDto> parcelaServiceDtos =
                registrarParcelasCoreMapper.toParcelaServiceDtos(registrarParcelasCoreDto);

        parcelaService.registrarParcelas(parcelaServiceDtos);
    }
}
