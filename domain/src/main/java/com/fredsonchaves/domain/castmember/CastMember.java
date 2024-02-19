package com.fredsonchaves.domain.castmember;

import com.fredsonchaves.domain.AggregateRoot;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.validation.ValidationHandler;
import com.fredsonchaves.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.UUID;

public class CastMember extends AggregateRoot<CastMemberID> {

    private String name;

    private CastMemberType type;

    private Instant createdAt;

    private Instant updatedAt;

    private CastMember(String name, CastMemberType type) {
        super(CastMemberID.from(UUID.randomUUID()));
        this.name = name;
        this.type = type;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        selfValidate();
    }

    private CastMember(
            CastMemberID castMemberID,
            String name,
            CastMemberType type,
            Instant createdAt,
            Instant updatedAt
    ) {
        super(castMemberID);
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private CastMember(CastMember castMember) {
        super(castMember.id);
        this.name = castMember.getName();
        this.type = castMember.getType();
        this.updatedAt = castMember.getUpdatedAt();
        this.createdAt = castMember.getCreatedAt();
    }

    public static CastMember newMember(String name, CastMemberType type) {
        return new CastMember(name, type);
    }

    public CastMember update(String name, CastMemberType type) {
        this.name = name;
        this.type = type;
        this.updatedAt = Instant.now();
        selfValidate();
        return this;
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CastMemberValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);
        if (notification.hasError()) throw new NotificationException("Failed to create a cast member", notification);
    }

    public static CastMember with(
            final CastMemberID id,
            final String name,
            final CastMemberType type,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new CastMember(id, name, type, createdAt, updatedAt);
    }

    public static CastMember with(final CastMember castMember) {
        return new CastMember(castMember);
    }

    public String getName() {
        return name;
    }

    public CastMemberType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
