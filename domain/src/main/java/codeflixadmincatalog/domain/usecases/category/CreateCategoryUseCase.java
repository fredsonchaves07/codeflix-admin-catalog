package codeflixadmincatalog.domain.usecases.category;

import codeflixadmincatalog.core.either.Either;
import codeflixadmincatalog.core.usecases.VoidUseCase;
import codeflixadmincatalog.core.valueobject.EmptyValueObject;
import codeflixadmincatalog.domain.entities.category.Category;
import codeflixadmincatalog.domain.errors.category.CategoryError;
import codeflixadmincatalog.domain.repositories.category.CategoryRepository;

public class CreateCategoryUseCase implements VoidUseCase<CreateCategoryInput> {

    private final CategoryRepository repository;

    public CreateCategoryUseCase(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Either<CategoryError, EmptyValueObject> execute(CreateCategoryInput input) {
        try {
            final Category category = Category.create(input.name(), input.description(), input.isActive());
            repository.save(category);
            return Either.success(EmptyValueObject.create());
        } catch (CategoryError error) {
            return Either.error(error);
        } catch (Exception exception) {
            return Either.error(CategoryError.trows(exception));
        }
    }
}
