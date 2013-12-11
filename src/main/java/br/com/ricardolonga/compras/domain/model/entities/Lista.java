package br.com.ricardolonga.compras.domain.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;

@Entity
@Table(name = "listas")
public class Lista extends BaseEntity<Lista> {

    private static final long serialVersionUID = 1L;

    private Descricao descricao;

    @OneToMany(fetch = FetchType.LAZY)
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

    public Set<Item> getItens() {
        return this.itens;
    }

    public void setItens(final Set<Item> itens) {
        this.itens = itens;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(descricao).append(itens).toHashCode();
    }

}