package com.fredsonchaves.infraestructure.castmember.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fredsonchaves.application.castmember.retrieve.list.CastMemberListOutput;

public record CastMemberListResponse(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type
) {
    public static CastMemberListResponse from(CastMemberListOutput output) {
        return new CastMemberListResponse(output.name(), output.type().name());
    }
}
