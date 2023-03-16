/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint;

import java.util.Arrays;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import com.ledmington.zint.ast.Node;

import gen.zombieLexer;
import gen.zombieParser;

public class Zint {

    public static void main(final String[] args) {
        Arrays.stream(args).forEach(System.out::println);

        final Node ast = getAST("banana is a zombie");
    }

    public static Node getAST(final String code) {
        final CharStream chars = CharStreams.fromString(code);
        final zombieLexer lexer = new zombieLexer(chars);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        for (Token token : tokens.getTokens()) {
            System.out.println(token);
        }
        final zombieParser parser = new zombieParser(tokens);

        final ParseTree st = parser.prog();

        final ZintVisitor visitor = new ZintVisitor();
        return visitor.visit(st);
    }
}
