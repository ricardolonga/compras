package br.com.ricardolonga.compras.infrastructure.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.ricardolonga.compras.domain.model.entities.BaseEntity;
import br.com.ricardolonga.compras.domain.repositories.IRepository;

public abstract class GenericDAO<T extends BaseEntity<?>> implements IRepository<T, Long> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> typeClass;

    public GenericDAO(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(typeClass, id);
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entityManager.merge(entity));
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
        entityManager.flush();
    }

    @Override
    public List<T> findAll() {
        String sql = "FROM " + typeClass.getSimpleName();
        return entityManager.createQuery(sql, typeClass).getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}