package com.fredsonchaves.domain.genre;

import com.fredsonchaves.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    @Test
    public void givenValidParamsWhenCallNewGenreShouldInstantiateAGenre() {
        final String expectedName = "Ação";
        final boolean expectedIsActive = true;
        final int expectedCategories = 0;
        Genre genre = Genre.newGenre(expectedName, expectedIsActive);
        assertNotNull(genre);
        assertNotNull(genre.getId());
        assertEquals(expectedName, genre.getName());
        assertEquals(expectedIsActive, genre.isActive());
        assertEquals(expectedCategories, genre.getCategories().size());
        assertNotNull(genre.getCreatedAt());
        assertNotNull(genre.getUpdatedAt());
        assertNull(genre.getDeletedAt());
    }

    @Test
    public void givenInvalidNullNameWhenCallNewGenreAndValidateShouldReveiveAError() {
        final String expectedName = null;
        final boolean expectedIsActive = true;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be null";
        NotificationException actualException = assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });
        assertEquals(expectedErrorCount, actualException.getErrors().stream().count());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidEmptyNameWhenCallNewGenreAndValidateShouldReveiveAError() {
        final String expectedName = " ";
        final boolean expectedIsActive = true;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be empty";
        NotificationException actualException = assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });
        assertEquals(expectedErrorCount, actualException.getErrors().stream().count());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenInvalidNameWithLengthMoreThan255WhenCallNewGenreAndValidateShouldReveiveAError() {
        final String expectedName = """
        O empenho em analisar a mobilidade dos capitais internacionais agrega valor ao estabelecimento do sistema
        de formação de quadros que corresponde às necessidades.
        O empenho em analisar a mobilidade dos capitais internacionais agrega valor ao estabelecimento do sistema
        de formação de quadros que corresponde às necessidades.;
        """;
        final boolean expectedIsActive = true;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' must be between 1 and 255 characters";
        NotificationException actualException = assertThrows(NotificationException.class, () -> {
            Genre.newGenre(expectedName, expectedIsActive);
        });
        assertEquals(expectedErrorCount, actualException.getErrors().stream().count());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
