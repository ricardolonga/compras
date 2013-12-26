package br.com.ricardolonga.compras.domain.builders;

import java.math.BigDecimal;

import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.model.valueobjects.Descricao;
import br.com.ricardolonga.compras.domain.model.valueobjects.Valor;

public class ProdutoBuilder {

    private final Produto produto = new Produto();

    public static ProdutoBuilder create() {
        return new ProdutoBuilder();
    }

    public ProdutoBuilder descricao(String descricao) {
        produto.setDescricao(Descricao.newInstance(descricao));
        return this;
    }

    public ProdutoBuilder valor(BigDecimal valor) {
        produto.setValorUnitario(Valor.newInstance(valor));
        return this;
    }

    public Produto build() {
        return produto;
    }

}
