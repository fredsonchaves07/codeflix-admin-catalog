package com.fredsonchaves.application.castmember.create;

import com.fredsonchaves.domain.castmember.CastMemberType;

public record CreateCastMemberCommand(
        String name,
        CastMemberType type
) {

    public CreateCastMemberCommand with(final String name, final CastMemberType castMemberType) {
        return new CreateCastMemberCommand(name, castMemberType);
    }
}
