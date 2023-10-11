package com.fredsonchaves.application.genre.update;

import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateGenreUseCaseTest {

    @InjectMocks
    private UpdateGenreUseCase genreUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Test
    public void givenAValidCommand_whenUpdateGenre_shouldReturnGenreId() {
        final Genre genre = Genre.newGenre("acao", true);
        final GenreID expectedId = genre.getId();
        final String expectedName = "Ação";
        final boolean expectedIsActive = true;
        final List<CategoryID> expectedCategories = List.of();
        final UpdateGenreCommand command = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));
        when(genreGateway.findById(any())).thenReturn(Optional.of(genre));
        when(genreGateway.update(any())).thenAnswer(returnsFirstArg());
        final UpdateGenreOutput actualOutput = genreUseCase.execute(command);
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());
        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(genreGateway, times(1)).update(argThat(updateGenre ->
                        Objects.equals(expectedId, updateGenre.getId())
                        && Objects.equals(expectedCategories, updateGenre.getCategories())
                                && Objects.equals(expectedIsActive, updateGenre.isActive())
                                && Objects.equals(expectedName, updateGenre.getName())
        ));

    }

    private List<String> asString(final List<CategoryID> ids) {
        return ids.stream().map(CategoryID::getValue).toList();
    }
}
