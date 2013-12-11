package br.com.ricardolonga.compras.domain.model.valueobjects;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;

@Embeddable
@Access(AccessType.PROPERTY)
public class Valor extends BaseValueObject<Valor> {

    private static final long serialVersionUID = 4312561153180157988L;

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

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

    @Override
    protected EqualsBuilder getEqualsBuilder(Valor other) {
        return new EqualsBuilder().append(valor, other.valor);
    }

}