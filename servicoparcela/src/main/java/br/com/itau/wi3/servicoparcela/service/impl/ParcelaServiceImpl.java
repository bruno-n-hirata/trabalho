package br.com.itau.wi3.servicoparcela.service.impl;

import br.com.itau.wi3.servicoparcela.integration.repository.ParcelaRepository;
import br.com.itau.wi3.servicoparcela.service.ParcelaService;
import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;
import br.com.itau.wi3.servicoparcela.service.mapper.ParcelaServiceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelaServiceImpl implements ParcelaService {

    private final ParcelaRepository parcelaRepository;
    private final ParcelaServiceMapper parcelaServiceMapper;

    @Override
    public void registrarParcelas(final List<ParcelaServiceDto> parcelaServiceDtos) {
        parcelaRepository.saveAll(parcelaServiceMapper.toParcelaEntities(parcelaServiceDtos));
    }
}
