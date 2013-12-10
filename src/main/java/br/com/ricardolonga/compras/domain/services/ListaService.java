package br.com.ricardolonga.compras.domain.services;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.ricardolonga.compras.domain.repositories.ListaRepository;

@Stateless
public class ListaService {

    @Inject
    private ListaRepository listaRepository;

}
