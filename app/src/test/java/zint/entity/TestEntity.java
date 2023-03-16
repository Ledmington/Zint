/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package zint.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class TestEntity {

    private Entity e;

    @Test
    public void noValue() {
        String name = "zombie";
        e = new DummyEntity(name);
        assertEquals(e.getName(), name);
        assertFalse(e.hasValue());
    }

    @Test
    public void withValue() {
        int n = 123;
        e = new DummyEntity("a", n);
        assertTrue(e.hasValue());
        assertEquals(e.getValue(), n);
    }
}
