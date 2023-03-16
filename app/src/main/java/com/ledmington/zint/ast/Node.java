/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint.ast;

public abstract class Node {

    private final int line;

    public Node(final int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }
}
