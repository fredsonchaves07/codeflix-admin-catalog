package com.fredsonchaves.infraestructure.genre;

import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import com.fredsonchaves.infraestructure.genre.persistence.GenreJpaEntity;
import com.fredsonchaves.infraestructure.genre.persistence.GenreRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GenreMySQLGateway implements GenreGateway {

    private final GenreRepository genreRepository;

    private final CategoryRepository categoryRepository;

    public GenreMySQLGateway(GenreRepository genreRepository, CategoryRepository categoryRepository) {
        this.genreRepository = genreRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Genre create(Genre genre) {
        return save(genre);
    }

    @Override
    public void deleteById(GenreID id) {

    }

    @Override
    public Optional<Genre> findById(GenreID id) {
        return Optional.empty();
    }

    @Override
    public Genre update(Genre genre) {
        return null;
    }

    @Override
    public Pagination<Genre> findAll(SearchQuery query) {
        return null;
    }

    private Genre save(Genre genre) {
        return genreRepository.save(GenreJpaEntity.from(genre)).toAggregate();
    }
}
