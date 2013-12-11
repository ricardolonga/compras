package br.com.ricardolonga.compras.domain.aggregates;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@Access(AccessType.PROPERTY)
public class Imagem {

    private byte[] imagem;

    @Lob
    @Column(nullable = true)
    public byte[] getConteudo() {
        return imagem;
    }

    public void setConteudo(byte[] conteudo) {
        this.imagem = conteudo;
    }

}