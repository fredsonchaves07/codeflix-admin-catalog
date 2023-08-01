package com.fredsonchaves.domain.genre;

import com.fredsonchaves.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class GenreID extends Identifier {

    protected final String value;

    private GenreID(String value) {
        Objects.requireNonNull(value, "'id' should not be null");
        this.value = value;
    }

    public static GenreID unique() {
        return GenreID.from(UUID.randomUUID());
    }

    public static GenreID from(final String id) {
        return new GenreID(id);
    }

    public static GenreID from(final UUID id) {
        return new GenreID(id.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GenreID that = (GenreID) object;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}
