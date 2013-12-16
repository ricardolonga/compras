package br.com.ricardolonga.compras.infrastructure.persistence.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import br.com.ricardolonga.compras.domain.model.entities.Lista;
import br.com.ricardolonga.compras.domain.repositories.IListaRepository;

@Stateless
public class ListaDAO extends GenericDAO<Lista> implements IListaRepository {

    public ListaDAO() {
        super(Lista.class);
    }

    @Override
    public Lista getListaCompletaById(Long id) {
        TypedQuery<Lista> findByIdQuery = entityManager.createQuery("SELECT DISTINCT l FROM Lista l LEFT JOIN FETCH l.itens WHERE l.id = :entityId ORDER BY l.id", Lista.class);
        findByIdQuery.setParameter("entityId", id);
        return findByIdQuery.getSingleResult();
    }

    @Override
    public List<Lista> getListasCompletas() {
        return entityManager.createQuery("SELECT DISTINCT l FROM Lista l LEFT JOIN FETCH l.itens ORDER BY l.id", Lista.class).getResultList();
    }

}
