package com.fredsonchaves.application;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class UseCaseTest {

    @Test
    public void testCreateUseCase() {
        assertNotNull(new UseCase());
        assertNotNull(new UseCase().execute());
    }
}
