package br.com.ricardolonga.compras.domain.model.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    private Set<Item> itens = new HashSet<Item>();

    @Embedded
    public Descricao getDescricao() {
        return this.descricao;
    }

    public void setDescricao(Descricao descricao) {
        this.descricao = descricao;
    }

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    @Override
    public String toString() {
        return descricao.getTexto();
    }

}