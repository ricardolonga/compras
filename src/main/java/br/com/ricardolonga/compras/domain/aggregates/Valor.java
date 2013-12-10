package br.com.ricardolonga.compras.domain.aggregates;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Valor {

    private BigDecimal valor;

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}