package com.fredsonchaves.infraestructure.castmember.models;

import java.time.Instant;

public record CreateCastMemberResponse(
        String id,
        String name,
        String type,
        Instant createdAt,
        Instant updatedAt
) {
}
