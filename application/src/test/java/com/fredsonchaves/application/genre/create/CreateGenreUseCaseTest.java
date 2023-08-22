package com.fredsonchaves.application.genre.create;

import com.fredsonchaves.domain.category.CategoryGateway;
import com.fredsonchaves.domain.category.CategoryID;
import com.fredsonchaves.domain.genre.GenreGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateGenreUseCaseTest {

    @InjectMocks
    private CreateGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAvalidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        final String expectName = "Ação";
        final boolean isActive = true;
        final List<CategoryID> expectedCategories = List.of();
        final CreateGenreCommand command = CreateGenreCommand.with(expectName, isActive, asString(expectedCategories));
        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());
        final CreateGenreOutput output = useCase.execute(command);
        assertNotNull(output);
        assertNotNull(output.id());
        verify(genreGateway).create(argThat(genre ->
            Objects.equals(expectName, genre.getName())
                    && Objects.equals(isActive, genre.isActive())
                    && Objects.equals(expectedCategories, genre.getCategories())
                    && Objects.nonNull(genre.getCreatedAt())
                    && Objects.nonNull(genre.getUpdatedAt())
                    && Objects.isNull(genre.getDeletedAt())
        ));
    }

    private List<String> asString(final List<CategoryID> categoryIDS) {
        return categoryIDS.stream().map(CategoryID::getValue).toList();
    }
}
