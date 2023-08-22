package com.fredsonchaves.domain.validation.handler;

import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable throwable) {
        return create(new Error(throwable.getMessage()));
    }

    public static Notification create(final Error error) {
        return new Notification(new ArrayList<>()).append(error);
    }

    @Override
    public Notification append(final Error error) {
        errors.add(error);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler handler) {
        errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public <T> T validate(final Validation<T> validation) {
        try {
            return validation.validate();
        } catch (final DomainException exception) {
            this.errors.addAll(exception.getErrors());
        } catch (Throwable throwable) {
            this.errors.add(new Error(throwable.getMessage()));
        }
        return null;
    }

    @Override
    public List<Error> getErrors() {
        return errors;
    }
}
