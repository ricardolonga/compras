package br.com.ricardolonga.compras.domain.model.valueobjects;

import org.apache.commons.lang.builder.EqualsBuilder;

public abstract class BaseValueObject<T extends BaseValueObject<?>> implements IValueObject<T> {

    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object other) {
        if (this == other)
            return true;

        if (other == null || getClass() != other.getClass())
            return false;

        return sameValueAs((T) other);
    }

    @Override
    public abstract int hashCode();

    @Override
    public boolean sameValueAs(final T other) {
        return other != null && getEqualsBuilder(other).isEquals();
    }

    protected abstract EqualsBuilder getEqualsBuilder(T other);

}
