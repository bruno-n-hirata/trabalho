package br.com.itau.wi3.servicoparcela.service;

import br.com.itau.wi3.servicoparcela.service.dto.ParcelaServiceDto;

import java.util.List;

public interface ParcelaService {
    void registrarParcelas(final List<ParcelaServiceDto> parcelaServiceDtos);
}
