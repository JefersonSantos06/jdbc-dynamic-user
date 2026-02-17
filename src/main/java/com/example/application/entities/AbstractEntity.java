package com.example.application.entities;

import java.util.Objects;

public abstract class AbstractEntity<T extends Number> {

    private T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final AbstractEntity<?> that = (AbstractEntity<?>) o;
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
