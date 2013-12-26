package br.com.ricardolonga.compras.domain.model.valueobjects;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;

@Embeddable
@SuppressWarnings("serial")
@Access(AccessType.PROPERTY)
public class Valor extends BaseValueObject<Valor> {

    private BigDecimal valor = new BigDecimal(0);

    public static Valor newInstance(BigDecimal valor) {
        Valor instance = new Valor();
        instance.setValor(valor);
        return instance;
    }

    @Column
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    protected EqualsBuilder getEqualsBuilder(Valor other) {
        return new EqualsBuilder().append(valor, other.valor);
    }

    @Override
    public String toString() {
        return valor.toString();
    }

}