package codeflixadmincatalog.core.entities;

import java.util.Objects;
import java.util.UUID;

public abstract class Identifier {

    private final String value;

    protected Identifier() {
        this.value = UUID.randomUUID().toString();
    }

    protected Identifier(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identifier that = (Identifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public static<E extends Entity<Identifier>> Identifier valueOf(E entity) {
        return entity.id();
    }
}
