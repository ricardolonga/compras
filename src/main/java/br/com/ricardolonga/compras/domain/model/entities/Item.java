package br.com.ricardolonga.compras.domain.model.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;
import br.com.ricardolonga.compras.domain.model.valueobjects.Imagem;
import br.com.ricardolonga.compras.domain.model.valueobjects.Valor;

@Entity
@Table(name = "itens")
public class Item extends BaseEntity<Item> {

    private static final long serialVersionUID = 1L;

    private Descricao descricao;

    private int quantidade = 1;

    private Imagem imagem;

    private Valor valorUnitario;

    Item() {}

    public static Item newInstance() {
        return new Item();
    }

    @Embedded
    public Descricao getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    @Column
    public int getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Embedded
    public Imagem getImagem() {
        return this.imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    @Embedded
    public Valor getValorUnitario() {
        return this.valorUnitario;
    }

    public void setValorUnitario(Valor valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(descricao).append(quantidade).append(imagem).append(valorUnitario).toHashCode();
    }

}