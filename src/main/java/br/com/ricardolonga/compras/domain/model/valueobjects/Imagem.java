package br.com.ricardolonga.compras.domain.model.valueobjects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

import org.apache.commons.lang.builder.EqualsBuilder;

@Embeddable
@Access(AccessType.PROPERTY)
public class Imagem extends BaseValueObject<Imagem> {

    private static final long serialVersionUID = -8908500114764102407L;

    private byte[] imagem;

    @Lob
    @Column(nullable = true)
    public byte[] getConteudo() {
        return imagem;
    }

    public void setConteudo(byte[] conteudo) {
        this.imagem = conteudo;
    }

    @Override
    protected EqualsBuilder getEqualsBuilder(Imagem other) {
        return new EqualsBuilder().append(imagem, other.imagem);
    }

    @Override
    public int hashCode() {
        return imagem.hashCode();
    }

}