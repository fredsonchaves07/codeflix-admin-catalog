package com.fredsonchaves.application.castmember.create;

import com.fredsonchaves.domain.castmember.CastMember;

public record CreateCastMemberOutput(
        String id
) {

    public static CreateCastMemberOutput from(CastMember castMember) {
        return new CreateCastMemberOutput(castMember.getId().getValue());
    }
}
