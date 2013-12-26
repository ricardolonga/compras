package br.com.ricardolonga.compras.domain.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.ricardolonga.compras.domain.model.entities.BaseEntity;

public interface IRepository<T extends BaseEntity<?>, K extends Serializable> {

    EntityManager getEntityManager();

    T findById(K id);

    void persist(T entity);

    void remove(T entity);

    List<T> findAll();

}
