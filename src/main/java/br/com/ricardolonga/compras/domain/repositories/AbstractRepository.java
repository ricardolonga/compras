package br.com.ricardolonga.compras.domain.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class AbstractRepository<T> {

    @Inject
    protected EntityManager entityManager;

}
