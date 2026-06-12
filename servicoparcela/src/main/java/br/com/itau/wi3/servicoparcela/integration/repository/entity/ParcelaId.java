package br.com.itau.wi3.servicoparcela.integration.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ParcelaId implements Serializable {

    @Column(name = "num_parc_gest_rene", nullable = false)
    private Short numeroParcela;

    @Column(name = "num_ctrt", nullable = false)
    private Long numeroContrato;

    @Column(name = "num_acor", nullable = false)
    private Long numeroAcordo;

    @Column(name = "cod_prod_opel", nullable = false)
    private Integer codigoProdutoOperacional;
}
