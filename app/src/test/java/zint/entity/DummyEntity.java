/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package zint.entity;

/*
	Used only for Entity testing.
*/
public class DummyEntity extends Entity {
    protected DummyEntity(final String name) {
        super(name);
    }

    protected DummyEntity(final String name, Integer value) {
        super(name, value);
    }

    public void summon() {}

    public void animate() {}

    public void disturb() {}

    public void bind() {}
}
