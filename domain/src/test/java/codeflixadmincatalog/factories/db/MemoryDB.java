package codeflixadmincatalog.factories.db;

import codeflixadmincatalog.core.entities.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MemoryDB<E> implements DB<E> {

    private final HashMap<String, E> data = new HashMap<>();

    private MemoryDB() {}

    public static <E> DB<E> createDB() {
        return new MemoryDB<>();
    }

    @Override
    public void add(String id, E value) {
        data.put(id, value);
    }

    @Override
    public List<E> findAll() {
        return List.copyOf(data.values());
    }

    @Override
    public Optional<E> findById(String id) {
        return Optional.of(data.get(id));
    }

    @Override
    public void delete(String id) {
        data.remove(id);
    }
}
