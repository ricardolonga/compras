package br.com.ricardolonga.compras.infrastructure.persistence.jpa;

import javax.ejb.Stateless;

import br.com.ricardolonga.compras.domain.model.entities.Produto;
import br.com.ricardolonga.compras.domain.repositories.IItemRepository;

@Stateless
public class ItemDAO extends GenericDAO<Produto> implements IItemRepository {

    public ItemDAO() {
        super(Produto.class);
    }

}
