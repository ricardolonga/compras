package br.com.ricardolonga.compras.domain.model.valueobjects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
@SuppressWarnings("serial")
@Access(AccessType.PROPERTY)
public class Descricao extends BaseValueObject<Descricao> {

    private String texto = "";

    @NotEmpty(message = "A descrição deve ser preenchida")
    @Column(name = "descricao", nullable = false)
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public int hashCode() {
        return texto.hashCode();
    }

    @Override
    protected EqualsBuilder getEqualsBuilder(Descricao other) {
        return new EqualsBuilder().append(texto, other.texto);
    }

    public static Descricao newInstance(String texto) {
        Descricao instance = new Descricao();
        instance.setTexto(texto);
        return instance;
    }

}