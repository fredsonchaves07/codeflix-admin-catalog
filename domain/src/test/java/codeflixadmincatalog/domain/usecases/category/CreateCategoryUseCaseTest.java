package codeflixadmincatalog.domain.usecases.category;

import codeflixadmincatalog.core.either.Either;
import codeflixadmincatalog.core.errors.TypeError;
import codeflixadmincatalog.core.valueobject.EmptyValueObject;
import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.errors.category.CategoryError;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;
import codeflixadmincatalog.factories.db.repositories.category.FailingCategoryRepository;
import codeflixadmincatalog.factories.db.repositories.category.InMemoryCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static codeflixadmincatalog.factories.entities.MakeCategory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateCategoryUseCaseTest {

    private CreateCategoryUseCase useCase;

    @BeforeEach
    void setUp() {
        CategoryRepository repository = InMemoryCategoryRepository.createRepository();
        useCase = new CreateCategoryUseCase(repository);
    }

    @Test
    public void shouldCreateANewCategory() {
        final Category category = makeCategory();
        final CreateCategoryInput input = CreateCategoryInput.with(
                category.name(), category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isSuccess());
        assertTrue(output.getSuccess().isPresent());
    }

    @Test
    public void shouldCreateANewCategoryWithEmptyDescription() {
        final Category category = makeCategoryWithEmptyDescription();
        final CreateCategoryInput input = CreateCategoryInput.with(
                category.name(), category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isSuccess());
        assertTrue(output.getSuccess().isPresent());
    }

    @Test
    public void shouldCreateANewCategoryWithInactive() {
        final Category category = makeCategoryWithInactive();
        final CreateCategoryInput input = CreateCategoryInput.with(
                category.name(), category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isSuccess());
        assertTrue(output.getSuccess().isPresent());
    }

    @Test
    public void shouldNotCreateANewCategoryWithInvalidNullName() {
        final String expectedErrorMessage = "Category name cannot be null";
        final Category category = makeCategory();
        final CreateCategoryInput input = CreateCategoryInput.with(
                null, category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isError());
        assertTrue(output.getError().isPresent());
        assertTrue(output.getError().get().isDomainError());
        assertEquals(expectedErrorMessage, output.getError().get().getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithEmptyNullName() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name cannot be empty";
        final CreateCategoryInput input = CreateCategoryInput.with(
                "", category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isError());
        assertTrue(output.getError().isPresent());
        assertTrue(output.getError().get().isDomainError());
        assertEquals(expectedErrorMessage, output.getError().get().getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameLessThan3Characters() {
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final CreateCategoryInput input = CreateCategoryInput.with(
                "ca", category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isError());
        assertTrue(output.getError().isPresent());
        assertTrue(output.getError().get().isDomainError());
        assertEquals(expectedErrorMessage, output.getError().get().getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryWithLengthNameMoreThan50Characters() {
        final String name = "Este é um exemplo de uma string longa com mais de 50 caracteres em Java, " +
                "para ilustrar a geração de strings extensas.";
        final Category category = makeCategory();
        final String expectedErrorMessage = "Category name must be between 3 to 50 characters";
        final CreateCategoryInput input = CreateCategoryInput.with(
                name, category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isError());
        assertTrue(output.getError().isPresent());
        assertTrue(output.getError().get().isDomainError());
        assertEquals(expectedErrorMessage, output.getError().get().getMessage());
    }

    @Test
    public void shouldNotCreateANewCategoryIfRepositoryInUseCaseIsNull() {
        useCase = new CreateCategoryUseCase(null);
        final Category category = makeCategory();
        final CreateCategoryInput input = CreateCategoryInput.with(
                category.name(), category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isError());
        assertTrue(output.getError().isPresent());
        assertTrue(output.getError().get().isInternalError());
    }

    @Test
    public void shouldNotCreateANewCategoryIfRepositoryThrowsException() {
        useCase = new CreateCategoryUseCase(FailingCategoryRepository.createRepository());
        final String expectedErrorMessage = "An error occurred while running the repository";
        final Category category = makeCategory();
        final CreateCategoryInput input = CreateCategoryInput.with(
                category.name(), category.description(), category.isActive()
        );
        Either<CategoryError, EmptyValueObject> output = useCase.execute(input);
        assertTrue(output.isError());
        assertTrue(output.getError().isPresent());
        assertTrue(output.getError().get().isInternalError());
        assertEquals(expectedErrorMessage, output.getError().get().getMessage());
    }
}
