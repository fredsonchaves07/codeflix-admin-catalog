package com.fredsonchaves.application.castmember.update;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.exceptions.DomainException;
import com.fredsonchaves.domain.exceptions.NotificationException;
import com.fredsonchaves.domain.validation.Error;
import com.fredsonchaves.domain.validation.handler.Notification;

public class UpdateCastMemberUseCase implements UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput> {

    public CastMemberGateway gateway;

    public UpdateCastMemberUseCase(final CastMemberGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public UpdateCastMemberOutput execute(UpdateCastMemberCommand command) {
        final Notification notification = Notification.create();
        CastMember castMember = gateway.findById(CastMemberID.from(command.id())).orElseThrow(
                () -> DomainException.with(new Error("Cast Member with id %s was not found".formatted(command.id())))
        );
        castMember.update(command.name(), command.type()).validate(notification);
        if (notification.hasError()) {
            notify(notification);
        }
        return UpdateCastMemberOutput.from(gateway.update(castMember));
    }

    private void notify(Notification notification) {
        throw new NotificationException("Could not create aggregate castMember", notification);
    }
}
