package com.fredsonchaves.domain.genre;

import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.Validator;

public class GenreValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;

    public static final int NAME_MIN_LENGTH = 3;

    private final Genre genre;

    protected GenreValidator(final Genre genre, final ValidationHandler handler) {
        super(handler);
        this.genre = genre;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final String name = genre.getName();
        if (name == null) {
            validationHandler().append(new Error("'name' should not be null"));
            return;
        }
        if (name.isBlank()) {
            validationHandler().append(new Error("'name' should not be empty"));
            return;
        }
        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            validationHandler().append(new Error("'name' must be between 1 and 255 characters"));
        }
    }
}
