package codeflixadmincatalog.core.usecases;

import codeflixadmincatalog.core.either.Either;
import codeflixadmincatalog.core.valueobject.EmptyValueObject;
import codeflixadmincatalog.core.valueobject.ValueObject;
import codeflixadmincatalog.domain.errors.category.CategoryError;

public interface VoidUseCase<IN extends ValueObject> {

    Either<CategoryError, EmptyValueObject> execute(IN input);
}
