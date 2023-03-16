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

public final class EntityDeclarationNode extends Node {

    private final IdNode id;
    private final EntityType entityType;
    private final List<InstructionNode> instructions;

    public EntityDeclarationNode(
            final IdNode id,
            final EntityType type,
            final List<InstructionNode> instructions,
            final ParserRuleContext ctx) {
        super(ctx);
        this.id = id;
        this.entityType = type;
        this.instructions = instructions;
    }

    public IdNode getID() {
        return id;
    }

    public EntityType getType() {
        return entityType;
    }

    public List<InstructionNode> getInstructions() {
        return instructions;
    }
}
