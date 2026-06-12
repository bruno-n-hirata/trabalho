package br.com.itau.wi3.servicoparcela.integration.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tbwi3004_parc_gest_rene")
public class ParcelaEntity {

    @EmbeddedId
    private ParcelaId id;

    @Column(name = "vlr_desc_parc", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorDescontoParcela; //TODO: Ver com o Flavio o nome da coluna na tabela

    @Column(name = "vlr_brut_parc", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorBrutoParcela;

    @Column(name = "vlr_ligo_parc", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorLiquidoParcela;
}
