package com.fredsonchaves.application.castmember.retrieve.get;

import com.fredsonchaves.application.UseCase;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.exceptions.NotFoundException;

import java.util.function.Supplier;

public class GetCastMemberByIdUseCase implements UseCase<CastMemberID, CastMemberOutput> {


    public final CastMemberGateway gateway;

    public GetCastMemberByIdUseCase(final CastMemberGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public CastMemberOutput execute(CastMemberID castMemberID) {
        return gateway.findById(castMemberID).map(CastMemberOutput::from).orElseThrow(notFound(castMemberID));
    }

    private Supplier<NotFoundException> notFound(final CastMemberID castMemberID) {
        return () -> NotFoundException.with(CastMember.class, castMemberID);
    }
}
