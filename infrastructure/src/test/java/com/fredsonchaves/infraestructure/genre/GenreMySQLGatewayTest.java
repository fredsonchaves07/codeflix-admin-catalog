package com.fredsonchaves.infraestructure.genre;

import com.fredsonchaves.config.annotations.MySQLGatewayTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.infraestructure.category.CategoryMySQLGateway;
import com.fredsonchaves.infraestructure.genre.persistence.GenreJpaEntity;
import com.fredsonchaves.infraestructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@MySQLGatewayTest
public class GenreMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryMySQLGateway;

    @Autowired
    private GenreMySQLGateway genreMySQLGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAValidGenreWithCategoryWhenCallsCreateGenreShouldPersistGenre() {
        Category category = categoryMySQLGateway.create(Category.newCategory("Filmes", null, true));
        final String expectedName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(category.getId());
        Genre genre = Genre.newGenre(expectedName, isActive);
        genre.addCategories(expectedCategories);
        assertEquals(0, genreRepository.count());
        genre = genreMySQLGateway.create(genre);
        assertEquals(expectedName, genre.getName());
        assertTrue(genre.isActive());
        assertEquals(expectedCategories, genre.getCategories());
        GenreJpaEntity genreJpaEntity = genreRepository.findById(genre.getId().getValue()).get();
        assertEquals(expectedName, genreJpaEntity.getName());
        assertTrue(genreJpaEntity.isActive());
        assertEquals(expectedCategories, genreJpaEntity.getCategoryIds());
    }

    @Test
    public void givenAValidGenreWhithoutCategoryWhenCallsCreateGenreShouldPersistGenre() {
        final String expectedName = "Ação";
        final boolean isActive = true;
        Genre genre = Genre.newGenre(expectedName, isActive);
        assertEquals(0, genreRepository.count());
        genre = genreMySQLGateway.create(genre);
        assertEquals(expectedName, genre.getName());
        assertTrue(genre.isActive());
        assertTrue(genre.getCategories().isEmpty());
        GenreJpaEntity genreJpaEntity = genreRepository.findById(genre.getId().getValue()).get();
        assertEquals(expectedName, genreJpaEntity.getName());
        assertTrue(genreJpaEntity.isActive());
        assertTrue(genreJpaEntity.getCategoryIds().isEmpty());
    }
}
