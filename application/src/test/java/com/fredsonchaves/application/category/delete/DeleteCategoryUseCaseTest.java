package com.fredsonchaves.application.category.delete;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidId_whenCallsDeleteCategory_shouldBeOk() {
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryID expectedId = category.getId();
        doNothing().when(gateway).deleteById(eq(expectedId));
        assertDoesNotThrow(() -> useCase.execute(expectedId));
        verify(gateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOk() {
        CategoryID expectedId = CategoryID.from(UUID.randomUUID());
        doNothing().when(gateway).deleteById(eq(expectedId));
        assertDoesNotThrow(() -> useCase.execute(expectedId));
        verify(gateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidId_whenGatewayThrowsError_shouldReturnException() {
        Category category = Category.newCategory("Filmes", "A categoria mais assistida", true);
        CategoryID expectedId = category.getId();
        doThrow(new IllegalStateException("Gateway Error")).when(gateway).deleteById(eq(expectedId));
        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId));
        verify(gateway, times(1)).deleteById(eq(expectedId));
    }
}
