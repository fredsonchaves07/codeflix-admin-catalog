package com.fredsonchaves.infrastructure;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MainTest {

    @Test
    public void testMain() {
        assertNotNull(new Main());
        Main.main(new String[]{});
    }
}
