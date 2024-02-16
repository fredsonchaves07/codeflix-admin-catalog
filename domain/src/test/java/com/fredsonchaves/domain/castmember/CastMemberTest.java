package com.fredsonchaves.domain.castmember;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CastMemberTest {

    @Test
    public void givenAValidParamsWhenCallsNewMemberThenInstantiateACastMember() {
        final var expectedName = "Vin Dielsel";
        final var expectedType = CastMemberType.ACTOR;
        final var actualMember = CastMember.newMember(expectedName, expectedType);
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
    }
}
