package com.fredsonchaves.application.genre.update;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.application.genre.create.CreateGenreCommand;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.NotificationException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private UpdateGenreUseCase genreUseCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

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

    @Test
    public void givenAValidCommandWithCategories_whenUpdateGenre_shouldReturnGenreId() {
        final Genre genre = Genre.newGenre("acao", true);
        final GenreID expectedId = genre.getId();
        final String expectedName = "Ação";
        final boolean expectedIsActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );
        final UpdateGenreCommand command = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));
        when(genreGateway.findById(any())).thenReturn(Optional.of(genre));
        when(categoryGateway.existsByIds(any())).thenReturn(expectedCategories);
        when(genreGateway.update(any())).thenAnswer(returnsFirstArg());
        final UpdateGenreOutput actualOutput = genreUseCase.execute(command);
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());
        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));
        verify(genreGateway, times(1)).update(argThat(updateGenre ->
                Objects.equals(expectedId, updateGenre.getId())
                        && Objects.equals(expectedCategories, updateGenre.getCategories())
                        && Objects.equals(expectedIsActive, updateGenre.isActive())
                        && Objects.equals(expectedName, updateGenre.getName())
        ));

    }

    @Test
    public void givenAnInvalidName_whenUpdateGenre_shouldNotificationException() {
        final Genre genre = Genre.newGenre("acao", true);
        final GenreID expectedId = genre.getId();
        final String expectedName = null;
        final boolean expectedIsActive = true;
        final String expectedErrorMessage = "'name' should not be null";
        final int expectedErrorCount = 1;
        final List<CategoryID> expectedCategories = List.of();
        final UpdateGenreCommand command = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));
        when(genreGateway.findById(any())).thenReturn(Optional.of(genre));
        final NotificationException actualOutput = assertThrows(
                NotificationException.class, () ->  {
                    genreUseCase.execute(command);
                });
        assertNotNull(actualOutput);
        verify(genreGateway, times(1)).findById(eq(expectedId));
        assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        assertEquals(expectedErrorMessage, actualOutput.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidEmptyName_WhenCallsUpdateGenre_shouldReturnDomainException() {
        final Genre genre = Genre.newGenre("acao", true);
        final GenreID expectedId = genre.getId();
        final String expectedName = "";
        final boolean expectedIsActive = true;
        final String expectedErrorMessage = "'name' should not be empty";
        final int expectedErrorCount = 1;
        final List<CategoryID> expectedCategories = List.of();
        final UpdateGenreCommand command = UpdateGenreCommand.with(expectedId.getValue(), expectedName, expectedIsActive, asString(expectedCategories));
        when(genreGateway.findById(any())).thenReturn(Optional.of(genre));
        final NotificationException actualOutput = assertThrows(
                NotificationException.class, () ->  {
                    genreUseCase.execute(command);
                });
        assertNotNull(actualOutput);
        verify(genreGateway, times(1)).findById(eq(expectedId));
        assertEquals(expectedErrorCount, actualOutput.getErrors().size());
        assertEquals(expectedErrorMessage, actualOutput.getErrors().get(0).message());
    }


    @Test
    public void givenAValidCommand_WhenUpdateCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException() {
        final Genre genre = Genre.newGenre("acao", true);
        final GenreID expectedId = genre.getId();
        final String expectedErrorMessage = "Some categories could not be found: 456, 789";
        final int expectedErrorCount = 1;
        final String expectName = "A��o";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456"),
                CategoryID.from("789")
        );
        final UpdateGenreCommand command = UpdateGenreCommand.with(expectedId.getValue(), expectName, isActive, asString(expectedCategories));
        when(categoryGateway.existsByIds(any())).thenReturn(List.of(CategoryID.from("123")));
        when(genreGateway.findById(any())).thenReturn(Optional.of(genre));
        final NotificationException actualException = assertThrows(NotificationException.class, () -> {
            genreUseCase.execute(command);
        });
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }


    private List<String> asString(final List<CategoryID> ids) {
        return ids.stream().map(CategoryID::getValue).toList();
    }
}
