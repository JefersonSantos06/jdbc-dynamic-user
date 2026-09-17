package io.t3w.app.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public abstract class T3WAbstractEntity<T extends T3WAbstractEntity<T, ID>, ID extends Number> {

    @Id
    @Column("registro")
    private ID id;

    public ID getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public T setId(final ID id) {
        this.id = id;
        return (T) this;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final var that = (T3WAbstractEntity<?,?>) o;
        if (id == null || that.id == null) {
            return false;
        }
        final var id = this.id.longValue();
        return id > 0 && id == that.id.longValue();
    }

    @Override
    public int hashCode() {
        if (id == null) {
            return 0;
        }
        final long value = id.longValue();
        if (value <= 0) {
            return 0;
        }
        return Long.hashCode(value);
    }
}
