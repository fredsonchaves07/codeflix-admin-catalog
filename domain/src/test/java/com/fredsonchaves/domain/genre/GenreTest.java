package com.fredsonchaves.domain.genre;

import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    @Test
    public void givenValidParamsWhenCallNewGenreShouldInstantiateAGenre() {
        final String expectedName = "A��o";
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
        de forma��o de quadros que corresponde �s necessidades.
        O empenho em analisar a mobilidade dos capitais internacionais agrega valor ao estabelecimento do sistema
        de forma��o de quadros que corresponde �s necessidades.;
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

    @Test
    public void givenAactiveGenreWhenCallInactivateShouldReceiveOK() {
        final String expectedName = "A��o";
        Genre genre = Genre.newGenre(expectedName, true);
        Instant actualUpdatedAt = genre.getUpdatedAt();
        assertTrue(genre.isActive());
        genre.deactivate();
        assertFalse(genre.isActive());
        assertNotEquals(actualUpdatedAt, genre.getUpdatedAt());
        assertNotNull(genre.getDeletedAt());
    }

    @Test
    public void givenAinactiveGenreWhenCallActivateShouldReceiveOK() {
        final String expectedName = "A��o";
        Genre genre = Genre.newGenre(expectedName, false);
        Instant actualUpdatedAt = genre.getUpdatedAt();
        assertFalse(genre.isActive());
        genre.activate();
        assertTrue(genre.isActive());
        assertNotEquals(actualUpdatedAt, genre.getUpdatedAt());
        assertNull(genre.getDeletedAt());
    }

    @Test
    public void givenAValidGenreWhenCallUpdateShouldReceiveOK() {
        final String expectedName = "A��o";
        final boolean expectedIsActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("651"),
                CategoryID.from("035")
        );
        Genre genre = Genre.newGenre("Teste", false);
        Instant actualUpdatedAt = genre.getUpdatedAt();
        genre.update(expectedName, expectedIsActive, expectedCategories);
        assertEquals(expectedName, genre.getName());
        assertEquals(expectedIsActive, genre.isActive());
        assertEquals(expectedCategories, genre.getCategories());
        assertNotEquals(actualUpdatedAt, genre.getUpdatedAt());
        assertNull(genre.getDeletedAt());
    }

    @Test
    public void giveanAValidGenreWhenCallUpdateWihNameIsEmpyShouldReturnException() {
        Genre genre = Genre.newGenre("Teste", false);
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be empty";
        NotificationException actualException = assertThrows(NotificationException.class, () -> {
            genre.update("", true, null);
        });
        assertEquals(expectedErrorCount, actualException.getErrors().stream().count());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void giveanAValidGenreWhenCallUpdateWihNameIsNullShouldReturnException() {
        Genre genre = Genre.newGenre("Teste", false);
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be null";
        NotificationException actualException = assertThrows(NotificationException.class, () -> {
            genre.update(null, true, null);
        });
        assertEquals(expectedErrorCount, actualException.getErrors().stream().count());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidEmptyCategoriesGenreWhenCallAddCategoriesShouldReceiveOK() {
        final String expectedName = "A��o";
        final boolean expectedIsActive = true;
        final List<CategoryID> expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("651"),
                CategoryID.from("035")
        );
        Genre genre = Genre.newGenre(expectedName, expectedIsActive);
        genre.addCategory(CategoryID.from("123"));
        genre.addCategory(CategoryID.from("651"));
        genre.addCategory(CategoryID.from("035"));
        assertEquals(expectedName, genre.getName());
        assertEquals(expectedIsActive, genre.isActive());
        assertEquals(expectedCategories, genre.getCategories());
        assertNull(genre.getDeletedAt());
    }

    @Test
    public void givenAValidCategoriesGenreWhenCallRemoveCategoriesShouldReceiveOK() {
        final String expectedName = "A��o";
        final boolean expectedIsActive = true;
        final List<CategoryID> categories = List.of(
                CategoryID.from("123"),
                CategoryID.from("651"),
                CategoryID.from("035")
        );
        Genre genre = Genre.newGenre(expectedName, expectedIsActive, categories);
        genre.removeCategory(CategoryID.from("123"));
        genre.removeCategory(CategoryID.from("651"));
        genre.removeCategory(CategoryID.from("035"));
        assertEquals(expectedName, genre.getName());
        assertEquals(expectedIsActive, genre.isActive());
        assertTrue(genre.getCategories().isEmpty());
        assertNull(genre.getDeletedAt());
    }
}
