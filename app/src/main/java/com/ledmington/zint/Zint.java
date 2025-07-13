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

import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Node;


public final class Zint {
    public static void main(final String[] args) {
        final ZombieParser parser = new ZombieParser();
        final Node ast = parser.parse("banana is a zombie summon remember 12 bind");
        System.out.println(ast);
    }
}
