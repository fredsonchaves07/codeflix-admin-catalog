package com.fredsonchaves.application.castmember.delete;

import com.fredsonchaves.application.UnitUseCase;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberID;

import java.util.Objects;

public class DeleteCastMemberUseCase implements UnitUseCase<CastMemberID> {

    private final CastMemberGateway gateway;

    public DeleteCastMemberUseCase(final CastMemberGateway gateway) {
        this.gateway = Objects.requireNonNull(gateway);
    }

    @Override
    public void execute(CastMemberID castMemberID) {
        gateway.deleteById(castMemberID);
    }
}
