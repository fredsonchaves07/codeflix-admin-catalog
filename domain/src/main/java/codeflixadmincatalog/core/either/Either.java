package codeflixadmincatalog.core.either;

import codeflixadmincatalog.core.errors.Error;
import codeflixadmincatalog.core.valueobject.ValueObject;

import java.util.Objects;
import java.util.Optional;

public abstract class Either<E extends Error, S extends ValueObject> {

    private Either() {}

    public abstract boolean isError();

    public abstract boolean isSuccess();

    public abstract Optional<E> getError();

    public abstract Optional<S> getSuccess();

    public static <E extends Error, S extends ValueObject> Either<E, S> error(E value) {
        return new EitherError<>(value);
    }

    public static <E extends Error, S extends ValueObject> Either<E, S> success(S value) {
        return new Success<>(value);
    }
    private static class EitherError<E extends Error, S extends ValueObject> extends Either<E, S> {
        private final E value;

        private EitherError(E value) {
            this.value = value;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public Optional<E> getError() {
            return Optional.of(value);
        }

        @Override
        public Optional<S> getSuccess() {
            return Optional.empty();
        }
    }

    private static class Success<E extends Error, S extends ValueObject> extends Either<E, S> {
        private final S value;

        private Success(S value) {
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public Optional<S> getSuccess() {
            return Optional.of(value);
        }

        @Override
        public Optional<E> getError() {
            return Optional.empty();
        }
    }
}
