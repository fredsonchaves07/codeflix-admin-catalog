package com.fredsonchaves.domain.castmember;

import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.Validator;

public class CastMemberValidator extends Validator {

    private final CastMember castMember;

    public CastMemberValidator(final CastMember castMember, final ValidationHandler handler) {
        super(handler);
        this.castMember = castMember;
    }

    @Override
    public void validate() {

    }
}
