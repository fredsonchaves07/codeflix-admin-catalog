package com.fredsonchaves.domain;

import com.fredsonchaves.domain.validation.ValidationHandler;

import java.util.Objects;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;

    public Entity(ID id) {
        Objects.requireNonNull(id, "'id' should not be null");
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public abstract void validate(ValidationHandler handler);

    @Override
    public boolean equals(final Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Entity<?> entity = (Entity<?>) object;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}