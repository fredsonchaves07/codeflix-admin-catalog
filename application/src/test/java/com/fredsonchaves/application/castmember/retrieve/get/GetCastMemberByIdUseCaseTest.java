package com.fredsonchaves.application.castmember.retrieve.get;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.castmember.CastMemberType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class GetCastMemberByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private GetCastMemberByIdUseCase useCase;

    @Mock
    private CastMemberGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidId_whenCallsGetCastMember_shouldReturnCastMember() {
        CastMember castMamber = CastMember.newMember("vin diesel", CastMemberType.ACTOR);
        final CastMemberID expectedId = castMamber.getId();
        final String expectedName = castMamber.getName();
        final CastMemberType expectedType = castMamber.getType();
        Instant expectedCreatedAt = castMamber.getCreatedAt();
        Instant expectedUpdatedAt = castMamber.getUpdatedAt();
        when(gateway.findById(expectedId)).thenReturn(Optional.of(castMamber.clone()));
        CastMemberOutput actualCastMember = useCase.execute(expectedId);
        assertEquals(CastMemberOutput.from(castMamber), actualCastMember);
        assertEquals(expectedId, actualCastMember.id());
        assertEquals(expectedName, actualCastMember.name());
        assertEquals(expectedType, actualCastMember.type());
        assertEquals(expectedCreatedAt, actualCastMember.createdAt());
        assertEquals(expectedUpdatedAt, actualCastMember.updatedAt());
    }
}
