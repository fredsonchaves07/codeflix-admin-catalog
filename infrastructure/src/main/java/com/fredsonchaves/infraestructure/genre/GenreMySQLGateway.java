package com.fredsonchaves.infraestructure.genre;

import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.genre.persistence.GenreJpaEntity;
import com.fredsonchaves.infraestructure.genre.persistence.GenreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GenreMySQLGateway implements GenreGateway {

    private final GenreRepository repository;

    public GenreMySQLGateway(final GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre create(final Genre genre) {
        return save(genre);
    }

    @Override
    public void deleteById(final GenreID id) {
        delete(id);
    }

    @Override
    public Optional<Genre> findById(final GenreID id) {
        return findGenre(id);
    }

    @Override
    public Genre update(final Genre genre) {
        return save(genre);
    }

    @Override
    public Pagination<Genre> findAll(final SearchQuery query) {
        final PageRequest page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort())
        );
        final Specification<GenreJpaEntity> specification = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(str ->
                        ((Specification<GenreJpaEntity>) (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(
                                        criteriaBuilder.upper(root.get("name")), "%" + str.toUpperCase() + "%")))
                .orElse(null);
        final Page<GenreJpaEntity> pageResult = repository.findAll(Specification.where(specification), page);
        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(
                        GenreJpaEntity::toAggregate
                ).stream().toList());
    }

    private Genre save(final Genre genre) {
        return repository.save(GenreJpaEntity.from(genre)).toAggregate();
    }

    private void delete(final GenreID genreID) {
        if (findGenre(genreID).isPresent()) {
            repository.delete(GenreJpaEntity.from(findGenre(genreID).orElseThrow()));
        }
    }

    private Optional<Genre> findGenre(final GenreID genreID) {
        Optional<GenreJpaEntity> genreJpa = repository.findById(genreID.getValue());
        if (genreJpa.isPresent())
            return Optional.of(repository.findById(genreID.getValue()).get().toAggregate());
        return Optional.empty();
    }
}
