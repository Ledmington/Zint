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
import com.ledmington.zint.ast.Node;
import com.ledmington.zint.ast.ProgBodyNode;
import com.ledmington.zint.ast.ProgNode;

import gen.zombieBaseVisitor;
import gen.zombieParser.EntityDeclarationContext;
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
        if (ctx.ZOMBIE() != null || ctx.ENSLAVED_UNDEAD() != null) {
            return new EntityDeclarationNode(new IdNode(ctx.ID().getText(), ctx), EntityType.ZOMBIE, ctx);
        }
        if (ctx.GHOST() != null || ctx.RESTLESS_UNDEAD() != null) {
            return new EntityDeclarationNode(new IdNode(ctx.ID().getText(), ctx), EntityType.GHOST, ctx);
        }
        if (ctx.VAMPIRE() != null || ctx.FREE_WILLED_UNDEAD() != null) {
            return new EntityDeclarationNode(new IdNode(ctx.ID().getText(), ctx), EntityType.VAMPIRE, ctx);
        }
        if (ctx.DEMON() != null) {
            return new EntityDeclarationNode(new IdNode(ctx.ID().getText(), ctx), EntityType.DEMON, ctx);
        }
        if (ctx.DJINN() != null) {
            return new EntityDeclarationNode(new IdNode(ctx.ID().getText(), ctx), EntityType.DJINN, ctx);
        }
        throw new RuntimeException("Unkown entity");
    }
}
