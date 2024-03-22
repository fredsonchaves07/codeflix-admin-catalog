package com.fredsonchaves.application.castmember.update;

import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberType;

public record UpdateCastMemberOutput(
        String id,
        String name,
        CastMemberType type
) {
    public static UpdateCastMemberOutput from(CastMember castMember) {
        return new UpdateCastMemberOutput(castMember.getId().getValue(), castMember.getName(), castMember.getType());
    }
}
