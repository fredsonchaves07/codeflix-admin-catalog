package com.fredsonchaves.domain.validation;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(final ValidationHandler handler) {
        this.handler = handler;
    }

    protected ValidationHandler validationHandler() {
        return handler;
    }

    public abstract void validate();
}
