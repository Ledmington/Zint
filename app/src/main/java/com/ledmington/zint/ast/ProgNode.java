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

public final class ProgNode extends Node {

    private final ProgBodyNode progBody;

    public ProgNode(final ProgBodyNode progBody, final ParserRuleContext ctx) {
        super(ctx);
        this.progBody = progBody;
    }

    public ProgBodyNode getProgBody() {
        return progBody;
    }
}
