/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import com.ledmington.zint.ast.EntityDeclarationNode;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.IdNode;
import com.ledmington.zint.ast.InstructionNode;
import com.ledmington.zint.ast.Node;
import com.ledmington.zint.ast.ProgBodyNode;
import com.ledmington.zint.ast.ProgNode;
import com.ledmington.zint.ast.RememberNode;

import gen.zombieBaseVisitor;
import gen.zombieParser.EntityDeclarationContext;
import gen.zombieParser.InstructionContext;
import gen.zombieParser.ProgContext;
import gen.zombieParser.ProgbodyContext;

public final class ZintVisitor extends zombieBaseVisitor<Node> {

    private static final String SINGLE_INDENTATION = "  ";
    private static final boolean debug = true;
    private static String indent = "";

    public ZintVisitor() {
        super();
    }

    private void printVarAndProdName(final ParserRuleContext ctx) {
        if (!debug) return;
        String prefix = "";
        Class<?> ctxClass = ctx.getClass(), parentClass = ctxClass.getSuperclass();
        if (!parentClass.equals(ParserRuleContext.class)) { // parentClass is the var context (and not ctxClass itself)
            prefix = Utils.lowerizeFirstChar(Utils.extractCtxName(parentClass.getName())) + ": production #";
        }
        System.out.println(indent + prefix + Utils.lowerizeFirstChar(Utils.extractCtxName(ctxClass.getName())));
    }

    @Override
    public Node visit(final ParseTree t) {
        if (t == null) {
            return null;
        }
        String temp = indent;
        indent = (indent == null) ? "" : indent + SINGLE_INDENTATION;
        Node result = super.visit(t);
        indent = temp;
        return result;
    }

    public Node visitProg(final ProgContext ctx) {
        printVarAndProdName(ctx);
        return new ProgNode((ProgBodyNode) visit(ctx.progbody()), ctx);
    }

    public Node visitProgbody(final ProgbodyContext ctx) {
        printVarAndProdName(ctx);
        return new ProgBodyNode(
                ctx.entityDeclaration().stream()
                        .map(decl -> (EntityDeclarationNode) visit(decl))
                        .toList(),
                ctx);
    }

    public Node visitEntityDeclaration(final EntityDeclarationContext ctx) {
        printVarAndProdName(ctx);
        final IdNode id = new IdNode(ctx.ID().getText(), ctx);
        EntityType type;

        if (ctx.ZOMBIE() != null || ctx.ENSLAVED_UNDEAD() != null) {
            type = EntityType.ZOMBIE;
        } else if (ctx.GHOST() != null || ctx.RESTLESS_UNDEAD() != null) {
            type = EntityType.GHOST;
        } else if (ctx.VAMPIRE() != null || ctx.FREE_WILLED_UNDEAD() != null) {
            type = EntityType.VAMPIRE;
        } else if (ctx.DEMON() != null) {
            type = EntityType.DEMON;
        } else if (ctx.DJINN() != null) {
            type = EntityType.DJINN;
        } else {
            throw new RuntimeException("Unkown entity");
        }

        return new EntityDeclarationNode(
                id,
                type,
                ctx.instruction().stream()
                        .map(inst -> (InstructionNode) visit(inst))
                        .toList(),
                ctx);
    }

    public Node visitInstruction(final InstructionContext ctx) {
        printVarAndProdName(ctx);

        if (ctx.REMEMBER() != null) {
            return new RememberNode(Integer.parseInt(ctx.NUMBER().getText()), ctx);
        } else {
            throw new RuntimeException("Unknown instruction");
        }
    }
}
