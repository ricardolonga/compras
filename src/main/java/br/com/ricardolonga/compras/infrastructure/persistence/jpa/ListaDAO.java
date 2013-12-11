package br.com.ricardolonga.compras.infrastructure.persistence.jpa;

import javax.ejb.Stateless;

import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.repositories.IListaRepository;

@Stateless
public class ListaDAO extends GenericDAO<Lista> implements IListaRepository {

    public ListaDAO() {
        super(Lista.class);
    }

}
