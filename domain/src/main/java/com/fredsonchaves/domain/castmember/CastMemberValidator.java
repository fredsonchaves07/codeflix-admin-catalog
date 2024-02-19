package com.fredsonchaves.domain.castmember;

import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 255;

    public static final int NAME_MIN_LENGTH = 3;

    private CastMember castMember;

    public CastMemberValidator(final CastMember castMember, final ValidationHandler handler) {
        super(handler);
        this.castMember = castMember;
    }

    @Override
    public void validate() {
        checkNameConstraints();
        checkTypeConstraints();
    }

    private void checkNameConstraints() {
        final String name = castMember.getName();
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
            validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }

    private void checkTypeConstraints() {
        final var type = castMember.getType();
        if (type == null) {
            validationHandler().append(new Error("'type' should not be null"));
        }
    }
}
