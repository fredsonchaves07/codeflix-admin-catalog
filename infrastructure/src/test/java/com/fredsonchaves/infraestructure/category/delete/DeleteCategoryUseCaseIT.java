package com.fredsonchaves.infraestructure.category.delete;

import com.fredsonchaves.application.category.delete.DeleteCategoryUseCase;
import com.fredsonchaves.config.annotations.IntegrationTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class DeleteCategoryUseCaseIT {

    @Autowired
    private DeleteCategoryUseCase useCase;

    @Autowired
    private CategoryRepository repository;

    @SpyBean
    private CategoryGateway gateway;

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOk() {
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryID expectedId = category.getId();
        assertEquals(0, repository.count());
        save(category);
        assertEquals(1, repository.count());
        assertDoesNotThrow(() -> useCase.execute(expectedId));
        assertEquals(0, repository.count());
    }

    private void save(final Category... category) {
        repository.saveAllAndFlush(List.of(category).stream().map(CategoryJpaEntity::from).toList());
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOk() {
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        assertEquals(0, repository.count());
        assertDoesNotThrow(() -> useCase.execute(expectedId));
        assertEquals(0, repository.count());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldReturnException() {
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryID expectedId = category.getId();
        doThrow(new IllegalStateException("Gateway Error")).when(gateway).deleteById(eq(expectedId));
        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId));
    }
}
