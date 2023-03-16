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

import com.ledmington.zint.ast.Node;
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
        return new ProgNode(visit(ctx.progbody()), ctx.get);
    }

    public Node visitProgbody(final ProgbodyContext ctx) {
        printVarAndProdName(ctx);
        for (EntityDeclarationContext decl : ctx.entityDeclaration()) {
            visit(decl);
        }
        return null;
    }

    public Node visitEntityDeclaration(final EntityDeclarationContext ctx) {
        printVarAndProdName(ctx);
        System.out.println(ctx.ID().getText());
        if (ctx.ZOMBIE() != null || ctx.ENSLAVED_UNDEAD() != null) {
            System.out.println("zombie");
        } else if (ctx.GHOST() != null || ctx.RESTLESS_UNDEAD() != null) {
            System.out.println("ghost");
        } else if (ctx.VAMPIRE() != null || ctx.FREE_WILLED_UNDEAD() != null) {
            System.out.println("vampire");
        } else if (ctx.DEMON() != null) {
            System.out.println("demon");
        } else if (ctx.DJINN() != null) {
            System.out.println("djinn");
        } else {
            throw new RuntimeException("Unkown entity");
        }
        return null;
    }
}
