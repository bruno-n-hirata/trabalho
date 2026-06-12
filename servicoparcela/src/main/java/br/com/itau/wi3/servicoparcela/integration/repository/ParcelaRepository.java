package br.com.itau.wi3.servicoparcela.integration.repository;

import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaEntity;
import br.com.itau.wi3.servicoparcela.integration.repository.entity.ParcelaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaRepository extends JpaRepository<ParcelaEntity, ParcelaId> {
}
