package com.fredsonchaves.application.genre.create;

import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.genre.GenreGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateGenreUseCaseTest {

    @InjectMocks
    private CreateGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAvalidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        final String expectName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of();
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());
        final CreateGenreOutput output = useCase.execute(command);
        assertNotNull(output);
        assertNotNull(output.id());
        verify(genreGateway).create(argThat(genre ->
            Objects.equals(expectName, genre.getName())
                    && Objects.equals(isActive, genre.isActive())
                    && Objects.equals(expectedCategories, genre.getCategories())
                    && Objects.nonNull(genre.getCreatedAt())
                    && Objects.nonNull(genre.getUpdatedAt())
                    && Objects.isNull(genre.getDeletedAt())
        ));
    }

    @Test
    public void givenAValidCommandWithCategories_WhenCallsCreateGenre_shouldReturnGenreId() {
        final String expectName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("351"),
                CategoryID.from("100"),
                CategoryID.from("412")
        );
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        when(categoryGateway.existsByIds(any())).thenReturn(expectedCategories);
        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());
        final CreateGenreOutput output = useCase.execute(command);
        assertNotNull(output);
        assertNotNull(output.id());
        verify(categoryGateway, times(1)).existsByIds(expectedCategories);
        verify(genreGateway).create(argThat(genre ->
                Objects.equals(expectName, genre.getName())
                        && Objects.equals(isActive, genre.isActive())
                        && Objects.equals(expectedCategories, genre.getCategories())
                        && Objects.nonNull(genre.getCreatedAt())
                        && Objects.nonNull(genre.getUpdatedAt())
                        && Objects.isNull(genre.getDeletedAt())
        ));
    }

    @Test
    public void givenAInvalidEmptyName_WhenCallsCreateGenre_shouldReturnDomainException() {
        final String expectedErrorMessage = "'name' should not be empty";
        final int expectedErrorCount = 1;
        final String expectName = " ";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of();
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        final NotificationException actualException = assertThrows(NotificationException.class, () -> {
            useCase.execute(command);
        });
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAInvalidNullName_WhenCallsCreateGenre_shouldReturnDomainException() {
        final String expectedErrorMessage = "'name' should not be null";
        final int expectedErrorCount = 1;
        final String expectName = null;
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of();
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        final NotificationException actualException = assertThrows(NotificationException.class, () -> {
            useCase.execute(command);
        });
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenAValidCommand_WhenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException() {
        final String expectedErrorMessage = "Some categories could not be found: 456, 789";
        final int expectedErrorCount = 1;
        final String expectName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456"),
                CategoryID.from("789")
        );
        when(categoryGateway.existsByIds(any())).thenReturn(List.of(CategoryID.from("123")));
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        final NotificationException actualException = assertThrows(NotificationException.class, () -> {
            useCase.execute(command);
        });
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void givenInvalidName_WhenCallsCreateGenreAndSomeCategoriesDoesNotExists_shouldReturnDomainException() {
        final String expectedErrorMessage1 = "Some categories could not be found: 456, 789";
        final String expectedErrorMessage2 = "'name' should not be null";
        final int expectedErrorCount = 2;
        final String expectName = null;
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456"),
                CategoryID.from("789")
        );
        when(categoryGateway.existsByIds(any())).thenReturn(List.of(CategoryID.from("123")));
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        final NotificationException actualException = assertThrows(NotificationException.class, () -> {
            useCase.execute(command);
        });
        assertNotNull(actualException);
        assertEquals(expectedErrorMessage1, actualException.getErrors().get(0).message());
        assertEquals(expectedErrorMessage2, actualException.getErrors().get(1).message());
        assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    private List<String> asString(final List<CategoryID> categoryIDS) {
        return categoryIDS.stream().map(CategoryID::getValue).toList();
    }
}
