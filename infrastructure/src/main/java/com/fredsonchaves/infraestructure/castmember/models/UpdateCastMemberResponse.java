package com.fredsonchaves.infraestructure.castmember.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateCastMemberResponse(
        @JsonProperty("name") String name,
        @JsonProperty("type") String type
) {
}
