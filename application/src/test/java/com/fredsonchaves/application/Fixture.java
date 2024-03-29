package com.fredsonchaves.application;

import com.fredsonchaves.domain.castmember.CastMemberType;
import com.github.javafaker.Faker;

public final class Fixture {

    public static final Faker FAKER = new Faker();

    public static String name() {
        return FAKER.name().fullName();
    }

    public static final class CastMember {
        public static CastMemberType type() {
            return FAKER.options().option(CastMemberType.ACTOR, CastMemberType.DIRECTOR);
        }
    }
}
