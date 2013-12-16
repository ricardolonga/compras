package br.com.ricardolonga.compras.domain.model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "itens")
@SuppressWarnings("serial")
@Access(AccessType.PROPERTY)
public class Item extends BaseEntity<Item> {

    private Produto produto;

    private int quantidade;

    private boolean adicionadoNoCarrinho;

    private Lista lista;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(final Produto produto) {
        this.produto = produto;
    }

    @Column
    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(final int quantidade) {
        this.quantidade = quantidade;
    }

    @Column
    public boolean getAdicionadoNoCarrinho() {
        return this.adicionadoNoCarrinho;
    }

    public void setAdicionadoNoCarrinho(final boolean adicionadoNoCarrinho) {
        this.adicionadoNoCarrinho = adicionadoNoCarrinho;
    }

    @ManyToOne
    public Lista getLista() {
        return this.lista;
    }

    public void setLista(final Lista lista) {
        this.lista = lista;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(produto).append(quantidade).append(adicionadoNoCarrinho).toHashCode();
    }

    @Override
    public String toString() {
        String result = getClass().getSimpleName() + " ";
        result += "produto: " + produto.getDescricao().getTexto();
        result += ", quantidade: " + quantidade;
        return result;
    }

}
