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

    public static CastMember newMember(String name, CastMemberType type) {
        return new CastMember(name, type);
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
