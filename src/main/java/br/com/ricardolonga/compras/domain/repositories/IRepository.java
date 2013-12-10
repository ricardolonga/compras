package br.com.ricardolonga.compras.domain.repositories;

import java.io.Serializable;
import java.util.List;

public interface IRepository<TYPE, KEY extends Serializable> {

    TYPE findById(KEY id);

    void update(TYPE entity);

    void persist(TYPE entity);

    void remove(TYPE entity);

    List<TYPE> findAll();

}
