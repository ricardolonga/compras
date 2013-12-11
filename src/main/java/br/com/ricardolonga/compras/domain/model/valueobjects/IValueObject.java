package br.com.ricardolonga.compras.domain.model.valueobjects;

import java.io.Serializable;

public interface IValueObject<T> extends Serializable {

    boolean sameValueAs(final T other);

}
