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

public final class RememberNode extends InstructionNode {

    private final int value;

    public RememberNode(final int value, final ParserRuleContext ctx) {
        super(ctx);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
