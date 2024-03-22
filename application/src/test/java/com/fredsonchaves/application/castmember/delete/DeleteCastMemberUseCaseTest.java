package com.fredsonchaves.application.castmember.delete;

import com.fredsonchaves.application.UseCaseTest;
import com.fredsonchaves.domain.castmember.CastMember;
import com.fredsonchaves.domain.castmember.CastMemberGateway;
import com.fredsonchaves.domain.castmember.CastMemberID;
import com.fredsonchaves.domain.castmember.CastMemberType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DeleteCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway gateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(gateway);
    }

    @Test
    public void givenAValidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        CastMember castMamber = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);
        final CastMemberID expectedId = castMamber.getId();
        doNothing().when(gateway).deleteById(any());
        assertDoesNotThrow(() -> useCase.execute(expectedId));
        verify(gateway, times(1)).deleteById(expectedId);
    }
}
