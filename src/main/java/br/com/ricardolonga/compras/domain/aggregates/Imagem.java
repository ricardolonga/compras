package br.com.ricardolonga.compras.domain.aggregates;

import javax.persistence.Embeddable;

@Embeddable
public class Imagem {

    private byte[] conteudo;

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

}