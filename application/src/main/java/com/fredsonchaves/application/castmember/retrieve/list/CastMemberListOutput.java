package com.fredsonchaves.application.castmember.retrieve.list;

import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.castmember.CastMemberType;

import java.time.Instant;

public record CastMemberListOutput(
        CastMemberID categoryID,
        String name,
        CastMemberType type,
        Instant createdAt
) {

    public static CastMemberListOutput from(final CastMember castMember) {
        return new CastMemberListOutput(
                castMember.getId(),
                castMember.getName(),
                castMember.getType(),
                castMember.getCreatedAt()
        );
    }
}
