package br.com.itau.gr7.registroboletos.entrypoint.controller.v1.request.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoJurosEnum {

    VALOR_POR_DIA(1, "Valor por dia"),
    TAXA_MENSAL(2, "Taxa mensal"),
    PERCENTUAL_POR_DIA(3, "Percentual por dia"),
    ISENTO(4, "Isento"),
    VALOR_MENSAL(5, "Valor mensal"),
    VALOR_FIXO(6, "Valor fixo"),
    ISENTO_2(7, "Isento"),
    PERCENTUAL_MENSAL(8, "Percentual mensal");

    private final int codigo;
    private final String descricao;
}
