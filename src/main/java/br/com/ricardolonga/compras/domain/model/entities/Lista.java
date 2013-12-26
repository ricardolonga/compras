package br.com.ricardolonga.compras.domain.model.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.HashCodeBuilder;

import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;

@Entity
@XmlRootElement
@Table(name = "listas")
@SuppressWarnings("serial")
@Access(AccessType.PROPERTY)
public class Lista extends BaseEntity<Lista> {

    private Descricao descricao = new Descricao();

    private List<Item> itens = new ArrayList<>();

    @Embedded
    public Descricao getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public List<Item> getItens() {
        return this.itens;
    }

    public void setItens(final List<Item> itens) {
        this.itens = itens;
    }

    @Transient
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : itens) {
            total = total.add(item.getTotal());
        }

        return total;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(descricao).append(itens).toHashCode();
    }

    @Override
    public String toString() {
        return descricao.getTexto();
    }

    public boolean temDescricao() {
        return descricao != null && descricao.getTexto() != null && !descricao.getTexto().isEmpty();
    }

}