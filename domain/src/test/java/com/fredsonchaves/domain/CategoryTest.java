package com.fredsonchaves.domain;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CategoryTest {

    @Test
    public void testNewCategory() {
        assertNotNull(new Category());
    }
}
