package codeflixadmincatalog.core.usecases;

import codeflixadmincatalog.core.either.Either;
import codeflixadmincatalog.core.errors.Error;
import codeflixadmincatalog.core.valueobject.ValueObject;

public interface UseCase<IN extends ValueObject, OUT extends ValueObject> {

    Either<Error, OUT> execute(IN input);
}
