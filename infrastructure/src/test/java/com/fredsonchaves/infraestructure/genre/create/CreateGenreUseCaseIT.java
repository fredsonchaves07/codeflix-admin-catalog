package com.fredsonchaves.infraestructure.genre.create;

import com.fredsonchaves.application.genre.create.CreateGenreCommand;
import com.fredsonchaves.application.genre.create.CreateGenreOutput;
import com.fredsonchaves.application.genre.create.CreateGenreUseCase;
import com.fredsonchaves.config.annotations.IntegrationTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.infraestructure.genre.persistence.GenreJpaEntity;
import com.fredsonchaves.infraestructure.genre.persistence.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IntegrationTest
public class CreateGenreUseCaseIT {

    @Autowired
    private CreateGenreUseCase useCase;

    @SpyBean
    private GenreGateway genreGateway;

    @SpyBean
    private CategoryGateway categoryGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void givenAvalidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        Category filmes = categoryGateway.create(Category.newCategory("Filmes", null, true));
        final String expectName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(filmes.getId());
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        final CreateGenreOutput output = useCase.execute(command);
        assertNotNull(output);
        assertNotNull(output.id());
        GenreJpaEntity actualGenre = genreRepository.findById(output.id()).get();
        assertNotNull(actualGenre.getCreatedAt());
        assertNull(actualGenre.getDeletedAt());
        assertNotNull(actualGenre.getUpdatedAt());
        assertEquals(expectName, actualGenre.getName());
        assertTrue(actualGenre.isActive());
    }

    private List<String> asString(final List<CategoryID> categoryIDS) {
        return categoryIDS.stream().map(CategoryID::getValue).toList();
    }
}
