package br.com.ricardolonga.compras.domain.repositories;

import javax.ejb.Stateless;

import br.com.ricardolonga.compras.domain.entities.Item;

@Stateless
public class ItemRepository extends AbstractJPARepository<Item> {

    public ItemRepository() {
        super(Item.class);
    }

}
