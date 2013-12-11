package br.com.ricardolonga.compras.domain.aggregates;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.PROPERTY)
public class Valor {

    private BigDecimal valor;

    Valor() {}

    public static Valor newInstance(BigDecimal valor) {
        if (valor == null) {
            throw new IllegalArgumentException();
        }

        Valor novoValor = new Valor();

        novoValor.setValor(valor);

        return novoValor;
    }

    @Column
    @NotNull
    public BigDecimal getValor() {
        return valor;
    }

    void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}