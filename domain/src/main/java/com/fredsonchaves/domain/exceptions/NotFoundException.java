package com.fredsonchaves.domain.exceptions;

import com.fredsonchaves.domain.AggregateRoot;
import com.fredsonchaves.domain.Identifier;
import com.fredsonchaves.domain.validation.Error;

import java.util.Collections;
import java.util.List;

public class NotFoundException extends DomainException {

    protected NotFoundException(final String message, final List<Error> errors) {
        super(message, errors);
    }

    public static NotFoundException with(
            final Class<? extends AggregateRoot<?>> aggregate,
            final Identifier id
    ) {
        final String error = "%s with ID %s was not found".formatted(aggregate.getSimpleName(), id.getValue());
        return new NotFoundException(error, Collections.emptyList());
    }
}
