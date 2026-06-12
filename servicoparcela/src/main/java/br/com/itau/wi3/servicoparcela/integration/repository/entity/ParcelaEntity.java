package br.com.itau.wi3.servicoparcela.integration.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "parcela")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaEntity {

    @EmbeddedId
    private ParcelaId id;

    @Column(name = "codigo_produto_operacional")
    private Integer codigoProdutoOperacional;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;
}
