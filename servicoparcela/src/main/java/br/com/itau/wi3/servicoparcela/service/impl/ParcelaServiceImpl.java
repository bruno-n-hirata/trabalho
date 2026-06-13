package br.com.itau.wi3.servicoparcela.service.impl;

import br.com.itau.wi3.servicoparcela.integration.repository.ParcelaRepository;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.service.ParcelaService;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import br.com.itau.wi3.servicoparcela.service.mapper.ParcelaServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParcelaServiceImpl implements ParcelaService {

    private final ParcelaRepository parcelaRepository;
    private final ParcelaServiceMapper parcelaServiceMapper;

    @Override
    @Transactional
    public void registrarParcelas(final List<ParcelaServiceDto> parcelaServiceDtos) {
        log.debug("Persistindo {} parcelas", parcelaServiceDtos.size());

        final List<ParcelaEntity> parcelaEntities = parcelaServiceMapper.toParcelaEntities(parcelaServiceDtos);
        parcelaRepository.saveAll(parcelaEntities);

        log.info("{} parcelas persistidas com sucesso", parcelaEntities.size());
    }
}
