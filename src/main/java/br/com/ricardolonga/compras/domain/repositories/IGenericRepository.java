package br.com.ricardolonga.compras.domain.repositories;

import java.io.Serializable;
import java.util.List;

import br.com.ricardolonga.compras.domain.model.entities.BaseEntity;

public interface IGenericRepository<T extends BaseEntity<?>, K extends Serializable> {

    T findById(K id);

    void persist(T entity);

    void remove(T entity);

    List<T> findAll();

}
