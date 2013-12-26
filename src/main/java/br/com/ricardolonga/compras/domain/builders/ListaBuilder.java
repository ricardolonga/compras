package br.com.ricardolonga.compras.domain.builders;

import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;

public class ListaBuilder {

    private final Lista lista = new Lista();

    public static ListaBuilder create() {
        return new ListaBuilder();
    }

    public ListaBuilder descricao(String descricao) {
        lista.setDescricao(Descricao.newInstance(descricao));
        return this;
    }

    public Lista build() {
        return lista;
    }

}
