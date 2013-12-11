package br.com.ricardolonga.compras.infrastructure.persistence.jpa;

import javax.ejb.Stateless;

import br.com.ricardolonga.compras.domain.model.entities.Item;
import br.com.ricardolonga.compras.domain.repositories.IItemRepository;

@Stateless
public class ItemDAO extends GenericDAO<Item> implements IItemRepository {

    public ItemDAO() {
        super(Item.class);
    }

}
