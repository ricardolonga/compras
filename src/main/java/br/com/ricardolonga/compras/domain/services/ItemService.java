package br.com.ricardolonga.compras.domain.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ricardolonga.compras.domain.repositories.ItemRepository;

@Stateless
public class ItemService {

    @Inject
    private ItemRepository itemRepository;

}
