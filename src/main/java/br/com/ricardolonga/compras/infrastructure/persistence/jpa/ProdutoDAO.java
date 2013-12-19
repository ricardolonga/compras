package br.com.ricardolonga.compras.infrastructure.persistence.jpa;

import javax.ejb.Stateless;

import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.repositories.IProdutoRepository;

@Stateless
public class ProdutoDAO extends GenericDAO<Produto> implements IProdutoRepository {

    public ProdutoDAO() {
        super(Produto.class);
    }

}
