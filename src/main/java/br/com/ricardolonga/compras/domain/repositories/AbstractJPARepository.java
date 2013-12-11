package br.com.ricardolonga.compras.domain.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class AbstractJPARepository<T> implements IRepository<T, Long> {

    @Inject
    protected EntityManager entityManager;

    private final Class<T> typeClass;

    public AbstractJPARepository(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(typeClass, id);
    }

    @Override
    public void update(T entity) {
        entityManager.persist(entityManager.merge(entity));
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    @Override
    public List<T> findAll() {
        String sql = "FROM " + typeClass.getSimpleName();
        return entityManager.createQuery(sql, typeClass).getResultList();
    }

}