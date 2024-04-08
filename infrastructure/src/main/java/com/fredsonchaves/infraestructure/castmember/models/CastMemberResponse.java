package com.fredsonchaves.infraestructure.castmember.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fredsonchaves.application.castmember.retrieve.get.CastMemberOutput;

import java.time.Instant;

public record CastMemberResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("type") String type,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
) {

    public static CastMemberResponse from(CastMemberOutput output) {
        return new CastMemberResponse(
                output.id().getValue(),
                output.name(),
                output.type().toString(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}
