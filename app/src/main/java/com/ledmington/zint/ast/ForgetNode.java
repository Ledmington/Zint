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

public final class ForgetNode extends InstructionNode {

    public ForgetNode(final ParserRuleContext ctx) {
        super(ctx);
    }
}
