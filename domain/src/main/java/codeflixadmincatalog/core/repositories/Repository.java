package codeflixadmincatalog.core.repositories;

import codeflixadmincatalog.core.entities.Entity;
import codeflixadmincatalog.core.entities.Identifier;

import java.util.List;
import java.util.Optional;

public interface Repository<I extends Identifier, E extends Entity<I>> {

    void save(E entity);

    Optional<E> findById(I id);

    List<E> findAll();

    void delete(E entity);

    void deleteAll();

    default long count() {
        return findAll().size();
    }
}
