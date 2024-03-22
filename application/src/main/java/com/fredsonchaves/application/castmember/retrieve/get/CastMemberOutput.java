package com.fredsonchaves.application.castmember.retrieve.get;

import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberOutput(
        CastMemberID id,
        String name,
        CastMemberType type,
        Instant createdAt,
        Instant updatedAt
) {

    public static CastMemberOutput from(final CastMember castMember) {
        return new CastMemberOutput(castMember.getId(), castMember.getName(), castMember.getType(), castMember.getCreatedAt(), castMember.getUpdatedAt());
    }
}
