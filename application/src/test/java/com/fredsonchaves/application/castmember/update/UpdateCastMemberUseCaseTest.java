package com.fredsonchaves.application.castmember.update;

import com.fredsonchaves.application.Fixture;
import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UpdateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private UpdateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnItsIdentifier() {
        CastMember castMamber = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);
        final var expectedId = castMamber.getId();
        final var expectedName = Fixture.name();
        final CastMemberType expectedType = Fixture.CastMember.type();
        final var aComand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);
        when(castMemberGateway.findById(any())).thenReturn(Optional.of(castMamber));
        when(castMemberGateway.update(any())).thenAnswer(returnsFirstArg());
        final var actualOutput = useCase.execute(aComand);
        assertNotNull(actualOutput);
        assertEquals(expectedId.getValue(), actualOutput.id());
        assertEquals(expectedName, actualOutput.name());
        assertEquals(expectedType, actualOutput.type());
    }
}
