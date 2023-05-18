package com.fredsonchaves.infraestructure.category;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.infraestructure.MySQLGatewayTest;
import com.fredsonchaves.infraestructure.category.persistence.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}
