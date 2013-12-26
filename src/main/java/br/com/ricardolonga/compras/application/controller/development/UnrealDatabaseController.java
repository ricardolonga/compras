package br.com.ricardolonga.compras.application.controller.development;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.ricardolonga.compras.domain.builders.ListaBuilder;
import br.com.ricardolonga.compras.domain.builders.ProdutoBuilder;
import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.repositories.IListaRepository;
import br.com.ricardolonga.compras.domain.repositories.IProdutoRepository;

@Named
@ApplicationScoped
public class UnrealDatabaseController implements Serializable {

    private static final long serialVersionUID = -1380978435250261291L;

    @Inject
    private IProdutoRepository produtoRepository;

    @Inject
    private IListaRepository listaRepository;

    private boolean criouOsDados;

    public void criaDadosFicticios() {
        if (criouOsDados) {
            return;
        }

        criaProdutosFicticios();
        criaListasFicticias();

        criouOsDados = true;
    }

    private void criaProdutosFicticios() {
        List<Produto> produtos = new ArrayList<>();

        produtos.add(ProdutoBuilder.create().descricao("Cueca").valor(new BigDecimal(12.34)).build());
        produtos.add(ProdutoBuilder.create().descricao("Shampoo").valor(new BigDecimal(6.80)).build());
        produtos.add(ProdutoBuilder.create().descricao("Desodorante").valor(new BigDecimal(5.90)).build());
        produtos.add(ProdutoBuilder.create().descricao("Bife").valor(new BigDecimal(2.34)).build());
        produtos.add(ProdutoBuilder.create().descricao("Cerveja").valor(new BigDecimal(2.30)).build());
        produtos.add(ProdutoBuilder.create().descricao("Creme dental").valor(new BigDecimal(9.90)).build());
        produtos.add(ProdutoBuilder.create().descricao("Sabão em pó").valor(new BigDecimal(15.40)).build());
        produtos.add(ProdutoBuilder.create().descricao("Vassoura").valor(new BigDecimal(12.00)).build());
        produtos.add(ProdutoBuilder.create().descricao("Pão integral").valor(new BigDecimal(4.85)).build());

        for (Produto produto : produtos) {
            produtoRepository.persist(produto);
        }
    }

    private void criaListasFicticias() {
        List<Lista> listas = new ArrayList<>();

        listas.add(ListaBuilder.create().descricao("Final de semana na praia").build());
        listas.add(ListaBuilder.create().descricao("Farmácia").build());

        for (Lista lista : listas) {
            listaRepository.persist(lista);
        }
    }

}
