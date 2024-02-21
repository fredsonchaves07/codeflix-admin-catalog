package com.fredsonchaves.application.castmember.create;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberType;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.validation.handler.Notification;

public class CreateCastMemberUseCase implements UseCase<CreateCastMemberCommand, CreateCastMemberOutput> {

    public CastMemberGateway gateway;

    public CreateCastMemberUseCase(final CastMemberGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CreateCastMemberOutput execute(CreateCastMemberCommand command) {
        String name = command.name();
        CastMemberType type = command.type();
        Notification notification = Notification.create();
        CastMember castMember = notification.validate(() -> CastMember.newMember(name, type));
        if (notification.hasError()) {
            notify(notification);
        }
        return CreateCastMemberOutput.from(gateway.create(castMember));
    }

    private void notify(Notification notification) {
        throw new NotificationException("Could not create aggregate castMember", notification);
    }
}
