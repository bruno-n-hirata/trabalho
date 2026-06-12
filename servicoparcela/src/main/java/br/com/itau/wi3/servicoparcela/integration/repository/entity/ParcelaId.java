package br.com.itau.wi3.servicoparcela.integration.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ParcelaId implements Serializable {

    @Column(name = "numero_contrato")
    private Long numeroContrato;

    @Column(name = "numero_parcela")
    private Integer numeroParcela;

    @Column(name = "numero_acordo")
    private Long numeroAcordo;

    @Column(name = "codigo_produto_operacional")
    private Integer codigoProdutoOperacional;
}
