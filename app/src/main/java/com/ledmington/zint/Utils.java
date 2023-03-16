/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package com.ledmington.zint;

public final class Utils {

    private Utils() {}

    public static String extractNodeName(String s) { // s is in the form compiler.AST$NameNode
        return s.substring(s.lastIndexOf('$') + 1, s.length() - 4);
    }

    public static String extractCtxName(String s) { // s is in the form compiler.FOOLParser$NameContext
        return s.substring(s.lastIndexOf('$') + 1, s.length() - 7);
    }

    public static String lowerizeFirstChar(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
