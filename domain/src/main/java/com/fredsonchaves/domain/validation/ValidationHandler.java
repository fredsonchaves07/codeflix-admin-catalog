package com.fredsonchaves.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(final Error error);

    ValidationHandler append(final ValidationHandler handler);

    ValidationHandler validate(final Validation validation);

    List<Error> getErrors();

    default Error firstError() {
        if (hasError())
            return getErrors().get(0);
        return null;
    }

    default boolean hasError() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    interface Validation {
        void validate();
    }
}
