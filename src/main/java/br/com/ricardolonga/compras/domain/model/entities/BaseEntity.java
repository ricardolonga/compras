package br.com.ricardolonga.compras.domain.model.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<?>> implements IEntity<T> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    protected Long id;

    @Version
    @Column(name = "version")
    private int version = 0;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object other) {
        if (this == other)
            return true;

        if (other == null || getClass() != other.getClass())
            return false;

        return sameIdentityAs((T) other);
    }

    @Override
    public boolean sameIdentityAs(T other) {
        return other != null && id != null && id.equals(other.getId());
    }

}