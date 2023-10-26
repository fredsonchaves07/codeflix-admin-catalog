package com.fredsonchaves.infraestructure.category;

import com.fredsonchaves.config.annotations.MySQLGatewayTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.pagination.Pagination;
import com.fredsonchaves.domain.pagination.SearchQuery;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
public class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway gateway;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void givenAValidCategory_whenCallsCreate_shouldReturnANewCategory() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A  categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        assertEquals(0, repository.count());
        Category actualCategory = gateway.create(category);
        assertEquals(1, repository.count());
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallsUpdate_shouldReturnACategoryUptaded() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A  categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory("Film", null, expectedIsActive);
        assertEquals(0, repository.count());
        repository.saveAndFlush(CategoryJpaEntity.from(category));
        assertEquals(1, repository.count());
        Category updatedCategory = category.clone().update(expectedName, expectedDescription, expectedIsActive);
        Category actualCategory = gateway.update(updatedCategory);
        assertEquals(1, repository.count());
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getCreatedAt());
        assertTrue(actualCategory.getUpdatedAt().isAfter(category.getUpdatedAt()));
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallsDeleteById_shouldDeleteCategory() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A  categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        assertEquals(0, repository.count());
        String categoryId = repository.saveAndFlush(CategoryJpaEntity.from(category)).getId();
        assertEquals(1, repository.count());
        gateway.deleteById(CategoryID.from(categoryId));
        assertEquals(0, repository.count());
    }

    @Test
    public void givenInvalidCategory_whenCallsDeleteById_shouldDeleteCategory() {
        assertEquals(0, repository.count());
        gateway.deleteById(CategoryID.from("invalid"));
        assertEquals(0, repository.count());
    }

    @Test
    public void givenAValidCategory_whenCallsFindyById_shouldReturnACategory() {
        final String expectedName = "Filmes";
        final String expectedDescription = "A  categoria mais assistida";
        final boolean expectedIsActive = true;
        final Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        assertEquals(0, repository.count());
        repository.saveAndFlush(CategoryJpaEntity.from(category));
        assertEquals(1, repository.count());
        final Category actualCategory = gateway.findById(category.getId()).get();
        assertEquals(1, repository.count());
        assertEquals(category.getId(), actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(actualCategory.getCreatedAt(), category.getCreatedAt());
        assertEquals(actualCategory.getUpdatedAt(), category.getUpdatedAt());
        assertEquals(actualCategory.getDeletedAt(), category.getDeletedAt());
    }

    @Test
    public void givenPrePersistedCategories_whenCallsFindAll_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;
        Category filmes = Category.newCategory("Filmes", null, true);
        Category series = Category.newCategory("S�ries", null, true);
        Category documentarios = Category.newCategory("Document�rios", null, true);
        assertEquals(0, repository.count());
        repository.saveAllAndFlush(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));
        assertEquals(3, repository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        Pagination<Category> actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenEmptyCategoriesTable_whenCallsFindAll_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;
        assertEquals(0, repository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        Pagination<Category> actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(0, actualResult.items().size());
    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnEmptyPage() {
        int expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;
        Category filmes = Category.newCategory("Filmes", null, true);
        Category series = Category.newCategory("S�ries", null, true);
        Category documentarios = Category.newCategory("Document�rios", null, true);
        assertEquals(0, repository.count());
        repository.saveAllAndFlush(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));
        assertEquals(3, repository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        Pagination<Category> actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
        expectedPage = 1;
        query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(filmes.getId(), actualResult.items().get(0).getId());
        expectedPage = 2;
        query = new SearchQuery(expectedPage, expectedPerPage, "", "name", "asc");
        actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(series.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndDocAsTerms_whenCallsFindAllAndTermsMatchsCategoryName_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;
        Category filmes = Category.newCategory("Filmes", null, true);
        Category series = Category.newCategory("S�ries", null, true);
        Category documentarios = Category.newCategory("Document�rios", null, true);
        assertEquals(0, repository.count());
        repository.saveAllAndFlush(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));
        assertEquals(3, repository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "doc", "name", "asc");
        Pagination<Category> actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndMaisAssistidaAsTerms_whenCallsFindAllAndTermsMatchsCategoryDescription_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 2;
        final var expectedTotal = 2;
        Category filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        Category series = Category.newCategory("S�ries", "Uma categoria assistida", true);
        Category documentarios = Category.newCategory("Document�rios", "A categoria mais assistida", true);
        assertEquals(0, repository.count());
        repository.saveAllAndFlush(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));
        assertEquals(3, repository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "mais assistida", "name", "asc");
        Pagination<Category> actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
        assertEquals(filmes.getId(), actualResult.items().get(1).getId());
    }

    @Test
    public void givenPrePersistedCategoriesAndMaisAssistidaAsTermsWithUpperCase_whenCallsFindAllAndTermsMatchsCategoryDescription_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 2;
        final var expectedTotal = 2;
        Category filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        Category series = Category.newCategory("S�ries", "Uma categoria assistida", true);
        Category documentarios = Category.newCategory("Document�rios", "A categoria mais assistida", true);
        assertEquals(0, repository.count());
        repository.saveAllAndFlush(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));
        assertEquals(3, repository.count());
        SearchQuery query = new SearchQuery(expectedPage, expectedPerPage, "MAIS ASSISTIDA", "name", "asc");
        Pagination<Category> actualResult = gateway.findAll(query);
        assertEquals(expectedPage, actualResult.currentPage());
        assertEquals(expectedPerPage, actualResult.perPage());
        assertEquals(expectedTotal, actualResult.total());
        assertEquals(expectedPerPage, actualResult.items().size());
        assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
        assertEquals(filmes.getId(), actualResult.items().get(1).getId());
    }
}
