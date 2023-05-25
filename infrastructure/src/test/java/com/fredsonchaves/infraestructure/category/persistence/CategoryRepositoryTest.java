package com.fredsonchaves.infraestructure.category.persistence;

import com.fredsonchaves.MySQLGatewayTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.infraestructure.category.CategoryMySQLGateway;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void givenAInvalidNullName_whenCallSave_shouldReturnError() {
        final String expectedMessage = "not-null property references a null or transient value : com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity.name";
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryJpaEntity categoryJpa = CategoryJpaEntity.from(category);
        categoryJpa.setName(null);
        DataIntegrityViolationException actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> repository.save(categoryJpa)
        );
        PropertyValueException actualCause = assertInstanceOf(
                PropertyValueException.class,
                actualException.getCause()
        );
        assertEquals("name", actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAInvalidNullCreatedAt_whenCallSave_shouldReturnError() {
        final String expectedMessage = "not-null property references a null or transient value : com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity.createdAt";
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryJpaEntity categoryJpa = CategoryJpaEntity.from(category);
        categoryJpa.setCreatedAt(null);
        DataIntegrityViolationException actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> repository.save(categoryJpa)
        );
        PropertyValueException actualCause = assertInstanceOf(
                PropertyValueException.class,
                actualException.getCause()
        );
        assertEquals("createdAt", actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }

    @Test
    public void givenAInvalidNullUpdatedAt_whenCallSave_shouldReturnError() {
        final String expectedMessage = "not-null property references a null or transient value : com.fredsonchaves.infraestructure.category.persistence.CategoryJpaEntity.updatedAt";
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryJpaEntity categoryJpa = CategoryJpaEntity.from(category);
        categoryJpa.setUpdatedAt(null);
        DataIntegrityViolationException actualException = assertThrows(
                DataIntegrityViolationException.class,
                () -> repository.save(categoryJpa)
        );
        PropertyValueException actualCause = assertInstanceOf(
                PropertyValueException.class,
                actualException.getCause()
        );
        assertEquals("updatedAt", actualCause.getPropertyName());
        assertEquals(expectedMessage, actualCause.getMessage());
    }
}
