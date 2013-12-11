package br.com.ricardolonga.compras.domain.aggregates;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.PROPERTY)
public class Descricao {

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

}