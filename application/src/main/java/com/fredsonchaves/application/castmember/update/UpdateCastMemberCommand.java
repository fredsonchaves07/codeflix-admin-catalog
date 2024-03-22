package com.fredsonchaves.application.castmember.update;

import com.fredsonchaves.domain.castmember.CastMemberType;

public record UpdateCastMemberCommand(
        String id,
        String name,
        CastMemberType type
) {
    public static UpdateCastMemberCommand with(final String id, final String name, final CastMemberType castMemberType) {
        return new UpdateCastMemberCommand(id, name, castMemberType);
    }
}
