package codeflixadmincatalog.factories.db;

import java.util.List;
import java.util.Optional;

public interface DB<E> {

    void add(String id, E value);

    List<E> findAll();

    Optional<E> findById(String id);

    void delete(String id);
}
