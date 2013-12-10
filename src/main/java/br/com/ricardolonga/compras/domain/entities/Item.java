package br.com.ricardolonga.compras.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.ricardolonga.compras.domain.aggregates.Descricao;
import br.com.ricardolonga.compras.domain.aggregates.Imagem;
import br.com.ricardolonga.compras.domain.aggregates.Valor;

@Entity
@Table(name = "itens")
public class Item extends AbstractEntity {

    private static final long serialVersionUID = -7346106688087648290L;

    private Descricao descricao;

    private int quantidade;

    private Imagem imagem;

    private Valor valorUnitario;

    Item() {}

    public static Item newInstance() {
        return new Item();
    }

    @Column
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

    @Column
    @Embedded
    public Imagem getImagem() {
        return this.imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    @Column
    @Embedded
    public Valor getValorUnitario() {
        return this.valorUnitario;
    }

    public void setValorUnitario(Valor valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

}