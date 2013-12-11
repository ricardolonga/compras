package br.com.ricardolonga.compras.domain.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ricardolonga.compras.domain.repositories.IListaRepository;

@Stateless
public class ListaService {

    @Inject
    private IListaRepository listaRepository;

}
