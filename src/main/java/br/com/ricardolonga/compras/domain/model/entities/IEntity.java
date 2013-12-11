package br.com.ricardolonga.compras.domain.model.entities;

import java.io.Serializable;

public interface IEntity<T> extends Serializable {

    boolean sameIdentityAs(T other);

}
