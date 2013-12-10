package br.com.ricardolonga.compras.domain.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
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

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Item> itens = new HashSet<Item>();

    Lista() {}

    public static Lista newInstance() {
        return new Lista();
    }

    @Column
    @Embedded
    public Descricao getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    public Set<Item> getItens() {
        return this.itens;
    }

    public void setItens(final Set<Item> itens) {
        this.itens = itens;
    }

}