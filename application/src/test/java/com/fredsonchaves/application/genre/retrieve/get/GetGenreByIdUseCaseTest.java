package com.fredsonchaves.application.genre.retrieve.get;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.NotFoundException;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GetGenreByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private GetGenreByIdUseCase useCase;

    @Mock
    private GenreGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidId_whenCallsGetGenre_shouldReturnGenre() {
        final Genre genre = Genre.newGenre("Ação", true);
        final GenreID expectedId = genre.getId();
        final String expectedName = "Ação";
        final boolean expectedIsActive = true;
        final List<CategoryID> expectedCategories = List.of();
        Instant expectedCreatedAt = genre.getCreatedAt();
        Instant expectedUpdatedAt = genre.getUpdatedAt();
        when(gateway.findById(expectedId)).thenReturn(Optional.of(genre.clone()));
        GenreOutput actualGenre = useCase.execute(expectedId);
        assertEquals(GenreOutput.from(genre), actualGenre);
        assertEquals(expectedId, actualGenre.id());
        assertEquals(expectedName, actualGenre.name());
        assertEquals(expectedIsActive, actualGenre.isActive());
        assertEquals(expectedCreatedAt, actualGenre.createdAt());
        assertEquals(expectedUpdatedAt, actualGenre.updatedAt());
        assertNull(actualGenre.deletedAt());
    }

    @Test
    public void givenAInvalidId_whenCallsGetGenre_shouldBeReturnNotFound() {
        GenreID expectedId = GenreID.from(UUID.randomUUID());
        final String expectedErrorMessage = "Genre with ID %s was not found".formatted(expectedId.getValue());
        when(gateway.findById(expectedId)).thenReturn(Optional.empty());
        final NotFoundException actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldReturnException() {
        final String expectedErrorMessage = "Gateway error";
        GenreID expectedId = GenreID.from(UUID.randomUUID());
        when(gateway.findById(expectedId)).thenThrow(new IllegalStateException(expectedErrorMessage));
        final IllegalStateException actualException = assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId)
        );
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
