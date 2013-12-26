package br.com.ricardolonga.compras.domain.model.entities;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;
import br.com.ricardolonga.compras.domain.model.valueobjects.Imagem;
import br.com.ricardolonga.compras.domain.model.valueobjects.Valor;

@Entity
@Table(name = "produtos")
@SuppressWarnings("serial")
@Access(AccessType.PROPERTY)
public class Produto extends BaseEntity<Produto> {

    private Descricao descricao = new Descricao();

    private Imagem imagem;

    private Valor valorUnitario = new Valor();

    @Embedded
    public Descricao getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
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
        return new HashCodeBuilder().append(id).append(descricao).append(imagem).append(valorUnitario).toHashCode();
    }

    @Override
    public String toString() {
        return descricao.getTexto();
    }

    public boolean temDescricao() {
        return descricao != null && descricao.getTexto() != null && !descricao.getTexto().isEmpty();
    }

}