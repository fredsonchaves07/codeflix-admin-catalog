package com.fredsonchaves.domain.validation.handler;

import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final Error error) {
        throw DomainException.with(List.of(error));
    }

    @Override
    public ValidationHandler append(final ValidationHandler handler) {
        throw DomainException.with(handler.getErrors());
    }

    @Override
    public ValidationHandler validate(final Validation validation) {
        try {
            validation.validate();
        } catch (Exception exception) {
            throw DomainException.with(List.of(new Error(exception.getMessage())));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
