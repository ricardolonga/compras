package br.com.ricardolonga.compras.domain.model.valueobjects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;

@Embeddable
@Access(AccessType.PROPERTY)
public class Descricao extends BaseValueObject<Descricao> {

    private static final long serialVersionUID = -6828614119310310055L;

    private String texto;

    Descricao() {}

    public static Descricao newInstance(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Nao eh permitido criar uma descricao vazia.");
        }

        Descricao novaDescricao = new Descricao();

        novaDescricao.setTexto(descricao);

        return novaDescricao;
    }

    @NotNull
    @Column(name = "descricao", nullable = false)
    public String getTexto() {
        return texto;
    }

    void setTexto(String texto) {
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

}