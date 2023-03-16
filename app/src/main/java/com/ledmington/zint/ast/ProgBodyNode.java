/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint.ast;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

public final class ProgBodyNode extends Node {

    private final List<EntityDeclarationNode> declarations;

    public ProgBodyNode(final List<EntityDeclarationNode> declarations, final ParserRuleContext ctx) {
        super(ctx);
        this.declarations = declarations;
    }

    public List<EntityDeclarationNode> getDeclarations() {
        return declarations;
    }
}