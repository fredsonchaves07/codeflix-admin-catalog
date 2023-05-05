package com.fredsonchaves.application.category.update;

import com.fredsonchaves.domain.category.Category;
import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway gateway;

    @Test
    public void givenAValidCommand_whenCalsUpdateCategory_shouldReturnCategoryId() {
        final Category category = Category.newCategory("Film", null, true);
        final String expectedName = "Filmes";
        final String expectedDescription = "A categoria mais assistida";
        final boolean expectedIsActive = true;
        final CategoryID expectedId = category.getId();
        final Instant expectedCreatedAt = category.getCreatedAt();
        when(gateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(category.clone()));
        when(gateway.update(any())).thenAnswer(returnsFirstArg());
        final UpdateCategoryInput input = UpdateCategoryInput.with(
                expectedId.getValue(), expectedName, expectedDescription, expectedIsActive
        );
        final UpdateCategoryOutput outuput = useCase.execute(input).get();
        assertNotNull(outuput);
        assertNotNull(outuput.id());
        verify(gateway, times(1)).findById(eq(expectedId));
        verify(gateway, times(1)).update(argThat(
                updatedCategory -> Objects.equals(expectedName, updatedCategory.getName())
                        && Objects.equals(expectedId, updatedCategory.getId())
                        && Objects.equals(expectedDescription, updatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, updatedCategory.isActive())
                        && Objects.equals(expectedCreatedAt, updatedCategory.getCreatedAt())
                        && category.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                        && Objects.isNull(updatedCategory.getDeletedAt())
        ));
    }
}
