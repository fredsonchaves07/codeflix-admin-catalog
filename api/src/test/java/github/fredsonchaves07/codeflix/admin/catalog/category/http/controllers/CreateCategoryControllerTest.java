package github.fredsonchaves07.codeflix.admin.catalog.category.http.controllers;

import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;
import github.fredsonchaves07.codeflix.admin.catalog.category.http.models.CreateCategoryResponse;
import github.fredsonchaves07.codeflix.admin.catalog.core.responses.ApiResponse;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.MockitoConfig;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static github.fredsonchaves07.codeflix.admin.catalog.factories.entities.MakeCategory.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@QuarkusTest
public class CreateCategoryControllerTest {

    @Inject
    CategoryRepository repository;

    @InjectMock
    @MockitoConfig(convertScopes = true)
    CategoryRepository repositoryMock;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldCreateANewCategory() {
        final Category newCategory = makeCategory();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                newCategory.name(), newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(201)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isPresent());
        assertFalse(repository.findAll().isEmpty());
        assertNotNull(category.get().id());
        assertEquals("Category created", response.message());
        assertEquals(RestResponse.StatusCode.CREATED, response.statusCode());
        assertEquals(newCategory.name(), category.get().name());
        assertEquals(newCategory.description(), category.get().description());
        assertEquals(newCategory.isActive(), category.get().isActive());
        assertNotNull(newCategory.createdAt());
        assertNotNull(newCategory.updatedAt());
        assertNull(newCategory.deletedAt());
    }

    @Test
    public void shouldCreateANewCategoryWithEmptyDescription() {
        final Category newCategory = makeCategoryWithEmptyDescription();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                newCategory.name(), newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(201)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isPresent());
        assertFalse(repository.findAll().isEmpty());
        assertNotNull(category.get().id());
        assertEquals("Category created", response.message());
        assertEquals(RestResponse.StatusCode.CREATED, response.statusCode());
        assertEquals(newCategory.name(), category.get().name());
        assertEquals(newCategory.description(), category.get().description());
        assertEquals(newCategory.isActive(), category.get().isActive());
        assertNotNull(newCategory.createdAt());
        assertNotNull(newCategory.updatedAt());
        assertNull(newCategory.deletedAt());
    }

    @Test
    public void shouldCreateANewCategoryWithInactive() {
        final Category newCategory = makeCategoryWithInactive();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                newCategory.name(), newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(RestResponse.StatusCode.CREATED)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isPresent());
        assertFalse(repository.findAll().isEmpty());
        assertNotNull(category.get().id());
        assertEquals("Category created", response.message());
        assertEquals(RestResponse.StatusCode.CREATED, response.statusCode());
        assertEquals(newCategory.name(), category.get().name());
        assertEquals(newCategory.description(), category.get().description());
        assertEquals(newCategory.isActive(), category.get().isActive());
        assertNotNull(newCategory.createdAt());
        assertNotNull(newCategory.updatedAt());
        assertNull(newCategory.deletedAt());
    }

    @Test
    public void shouldNotCreateANewCategoryWithInvalidNullName() {
        final String expectedErrorMessage = "Category name cannot be null";
        final Category newCategory = makeCategory();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                null, newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(RestResponse.StatusCode.BAD_REQUEST)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isEmpty());
        assertTrue(repository.findAll().isEmpty());
        assertEquals(expectedErrorMessage, response.message());
        assertEquals(RestResponse.StatusCode.BAD_REQUEST, response.statusCode());
    }

    @Test
    public void shouldNotCreateANewCategoryWithEmptyNullName() {
        final String expectedErrorMessage = "Category name cannot be empty";
        final Category newCategory = makeCategory();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                "", newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(RestResponse.StatusCode.BAD_REQUEST)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isEmpty());
        assertTrue(repository.findAll().isEmpty());
        assertEquals(expectedErrorMessage, response.message());
        assertEquals(RestResponse.StatusCode.BAD_REQUEST, response.statusCode());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameLessThan3Characters() {
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final Category newCategory = makeCategory();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                "ca", newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(RestResponse.StatusCode.BAD_REQUEST)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isEmpty());
        assertTrue(repository.findAll().isEmpty());
        assertEquals(expectedErrorMessage, response.message());
        assertEquals(RestResponse.StatusCode.BAD_REQUEST, response.statusCode());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameMoreThan50Characters() {
        final String name = "Este é um exemplo de uma string longa com mais de 50 caracteres em Java, " +
                "para ilustrar a geração de strings extensas.";
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final Category newCategory = makeCategory();
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                name, newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(RestResponse.StatusCode.BAD_REQUEST)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isEmpty());
        assertTrue(repository.findAll().isEmpty());
        assertEquals(expectedErrorMessage, response.message());
        assertEquals(RestResponse.StatusCode.BAD_REQUEST, response.statusCode());
    }

    @Test
    public void shouldNotCreateANewCategoryIfRepositoryIsNull() {
        final Category newCategory = makeCategory();
        final String expectedErrorMessage = "Internal Server Error. Consult your system administrator";
        doThrow(NullPointerException.class).when(repositoryMock).save(any());
        final CreateCategoryResponse input = CreateCategoryResponse.with(
                newCategory.name(), newCategory.description(), newCategory.isActive()
        );
        ApiResponse<?> response = given()
                .contentType(ContentType.JSON)
                .body(input)
                .when()
                .post("/categories")
                .then()
                .statusCode(500)
                .extract()
                .response()
                .as(ApiResponse.class);
        final Optional<Category> category = repository.findAll().stream().findFirst();
        assertTrue(response.data().isEmpty());
        assertTrue(category.isEmpty());
        assertTrue(repository.findAll().isEmpty());
        assertEquals(expectedErrorMessage, response.message());
        assertEquals(RestResponse.StatusCode.INTERNAL_SERVER_ERROR, response.statusCode());
    }
}
