package com.example.application.entities;

public abstract class AbstractEntity<T extends AbstractEntity<T, ID>, ID extends Number> {

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
        final var that = (AbstractEntity<?,?>) o;
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
