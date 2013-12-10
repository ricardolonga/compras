package br.com.ricardolonga.compras.infrastructure;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProduces {

    @Produces
    @PersistenceContext
    private EntityManager entityManager;

}
