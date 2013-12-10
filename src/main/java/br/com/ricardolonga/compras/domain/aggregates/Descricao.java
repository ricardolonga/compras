package br.com.ricardolonga.compras.domain.aggregates;

import javax.persistence.Embeddable;

@Embeddable
public class Descricao {

    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}