package com.fredsonchaves.domain.castmember;

import com.fredsonchaves.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CastMemberTest {

    @Test
    public void givenAValidParamsWhenCallsNewMemberThenInstantiateACastMember() {
        final var expectedName = "Vin Dielsel";
        final var expectedType = CastMemberType.ACTOR;
        final var actualMember = CastMember.newMember(expectedName, expectedType);
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
    }

    @Test
    public void givenAInvalidNullNameWhenCallsNewMemberShouldReceiveANotificacation() {
        final var expectedType = CastMemberType.ACTOR;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be null";
        NotificationException notificationException = assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(null, expectedType)
        );
        assertNotNull(notificationException);
        assertEquals(expectedErrorCount, notificationException.getErrors().size());
        assertEquals(expectedErrorMessage, notificationException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidEmptyNameWhenCallsNewMemberShouldReceiveANotificacation() {
        final var expectedName = " ";
        final var expectedType = CastMemberType.ACTOR;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' should not be empty";
        NotificationException notificationException = assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );
        assertNotNull(notificationException);
        assertEquals(expectedErrorCount, notificationException.getErrors().size());
        assertEquals(expectedErrorMessage, notificationException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNameWithMoreThan255WhenCallsNewMemberShouldReceiveANotificacation() {
        final var expectedName = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur? At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga.";
        final var expectedType = CastMemberType.ACTOR;
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'name' must be between 3 and 255 characters";
        NotificationException notificationException = assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, expectedType)
        );
        assertNotNull(notificationException);
        assertEquals(expectedErrorCount, notificationException.getErrors().size());
        assertEquals(expectedErrorMessage, notificationException.getErrors().get(0).message());
    }

    @Test
    public void givenAInvalidNullTypeNameWhenCallsNewMemberShouldReceiveANotificacation() {
        final var expectedName = "Vin Dielsel";
        final int expectedErrorCount = 1;
        final String expectedErrorMessage = "'type' should not be null";
        NotificationException notificationException = assertThrows(
                NotificationException.class,
                () -> CastMember.newMember(expectedName, null)
        );
        assertNotNull(notificationException);
        assertEquals(expectedErrorCount, notificationException.getErrors().size());
        assertEquals(expectedErrorMessage, notificationException.getErrors().get(0).message());
    }

    @Test
    public void givenAValidCastMemberWhenCallsUpdateShouldReceiveUpdated() {
        final var expectedName = "Vin Dielsel";
        final var expectedType = CastMemberType.ACTOR;
        final var actualMember = CastMember.newMember("vind", CastMemberType.DIRECTOR);
        actualMember.update(expectedName, expectedType);
        assertEquals(expectedName, actualMember.getName());
        assertEquals(expectedType, actualMember.getType());
    }
}
