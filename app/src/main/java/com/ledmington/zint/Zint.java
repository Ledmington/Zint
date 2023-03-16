/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint;

import java.io.InputStream;
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

    public static final InputStream fin = System.in;

    public static void main(final String[] args) {
        Arrays.stream(args).forEach(System.out::println);
        final ZintVisitor visitor = new ZintVisitor();

        final CharStream chars = CharStreams.fromString("banana is a zombie");
        final zombieLexer lexer = new zombieLexer(chars);
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        for (Token token : tokens.getTokens()) {
            System.out.println(token);
        }
        final zombieParser parser = new zombieParser(tokens);

        final ParseTree st = parser.prog();

        System.out.println("Generating AST");
        final Node ast = visitor.visit(st);
    }
}
