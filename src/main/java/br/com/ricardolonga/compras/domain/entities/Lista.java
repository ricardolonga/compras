package br.com.ricardolonga.compras.domain.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import br.com.ricardolonga.compras.domain.aggregates.Descricao;

@Entity
@Table(name = "listas")
public class Lista extends AbstractEntity {

    private static final long serialVersionUID = -1576978480616329305L;

    private Descricao descricao;

    private Set<Item> itens = new HashSet<Item>();

    Lista() {}

    public static Lista newInstance() {
        return new Lista();
    }

    @Embedded
    public Descricao getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    public Set<Item> getItens() {
        return this.itens;
    }

    public void setItens(final Set<Item> itens) {
        this.itens = itens;
    }

}