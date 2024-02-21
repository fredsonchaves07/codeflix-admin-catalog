package com.fredsonchaves.application.castmember.create;

import com.fredsonchaves.application.Fixture;
import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private CreateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidCommandWhenCallsCreateMemberShouldReturnIt() {
        String expectedName = Fixture.name();
        CastMemberType expectedType = Fixture.CastMember.type();
        final var aCommand = new CreateCastMemberCommand(expectedName, expectedType);
        when(gateway.create(any())).thenAnswer(returnsFirstArg());
        final var output = useCase.execute(aCommand);
        assertNotNull(output);
        verify(gateway).create(argThat(member ->
                Objects.equals(expectedName, member.getName())
                        && Objects.equals(expectedType, member.getType())
        ));
    }
}
