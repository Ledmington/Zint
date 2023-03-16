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

public final class EntityDeclarationNode extends Node {

    private final IdNode id;
    private final EntityType entityType;

    public EntityDeclarationNode(final IdNode id, final EntityType type, final ParserRuleContext ctx) {
        super(ctx);
        this.id = id;
        this.entityType = type;
    }

    public IdNode getID() {
        return id;
    }

    public EntityType getType() {
        return entityType;
    }
}
