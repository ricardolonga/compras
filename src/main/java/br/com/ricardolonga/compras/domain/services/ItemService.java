package br.com.ricardolonga.compras.domain.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ricardolonga.compras.domain.repositories.IItemRepository;

@Stateless
public class ItemService {

    @Inject
    private IItemRepository itemRepository;

}
