package br.com.ricardolonga.compras.domain.repositories;

import javax.ejb.Stateless;

import br.com.ricardolonga.compras.domain.entities.Lista;

@Stateless
public class ListaRepository extends AbstractJPARepository<Lista> {

    public ListaRepository() {
        super(Lista.class);
    }

}
