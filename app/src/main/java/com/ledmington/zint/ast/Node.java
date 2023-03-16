/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint.ast;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Node {

    private final int line;
    private final int column;

    public Node(final ParserRuleContext ctx) {
        this.line = ctx.getStart().getLine();
        this.column = ctx.getStart().getCharPositionInLine();
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
