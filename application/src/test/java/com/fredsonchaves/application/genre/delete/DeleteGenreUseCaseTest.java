package com.fredsonchaves.application.genre.delete;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.genre.Genre;
import com.fredsonchaves.domain.genre.GenreGateway;
import com.fredsonchaves.domain.genre.GenreID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DeleteGenreUseCase deleteGenreUseCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        final Genre genre = Genre.newGenre("Ação", true);
        final GenreID expectedId = genre.getId();
        doNothing().when(genreGateway).deleteById(any());
        assertDoesNotThrow(() -> deleteGenreUseCase.execute(expectedId));
        verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAnInValidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        final GenreID expectedId = GenreID.from("123");
        doNothing().when(genreGateway).deleteById(any());
        assertDoesNotThrow(() -> deleteGenreUseCase.execute(expectedId));
        verify(genreGateway, times(1)).deleteById(expectedId);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenreAndGatewaysThrowsUnexpectedError_shouldReceiveException() {
        final Genre genre = Genre.newGenre("Ação", true);
        final GenreID expectedId = genre.getId();
        doThrow(new IllegalStateException("Gateway error")).when(genreGateway).deleteById(expectedId);
        assertThrows(IllegalStateException.class, () -> deleteGenreUseCase.execute(expectedId));
        verify(genreGateway, times(1)).deleteById(expectedId);
    }
}
