/*
 * Zint - ZOMBIE interpreter
 * Copyright (C) 2022-2025 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ledmington.zint;

import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Node;
import com.ledmington.zint.gen.ZombieParser.OneOrMore;
import com.ledmington.zint.gen.ZombieParser.Or;
import com.ledmington.zint.gen.ZombieParser.Sequence;
import com.ledmington.zint.gen.ZombieParser.Terminal;

import java.util.List;

public final class Zint {

	private static void printNode(final Node n, final String indent, final String continuationIndent) {
		final char verticalLine = '│';
		final char horizontalLine = '─';
		final char joint = '├';
		final char angle = '└';
		switch (n) {
			case Terminal t -> System.out.println(indent + t);
			case Or or -> {
				System.out.println(indent + "Or");
				printNode(or.inner(), continuationIndent + " " + angle + horizontalLine, continuationIndent + "   ");
			}
			case Sequence s -> {
				System.out.println(indent + "Sequence");
				final List<Node> children = s.nodes();
				final int len = children.size();
				for (int i = 0; i < len - 1; i++) {
					printNode(children.get(i), continuationIndent + " " + joint + horizontalLine, continuationIndent + ' ' + verticalLine + ' ');
				}
				printNode(children.getLast(), continuationIndent + " " + angle + horizontalLine, continuationIndent + "   ");
			}
			case OneOrMore oom -> {
				System.out.println(indent + "OneOrMore");
				final List<Node> children = oom.nodes();
				final int len = children.size();
				for (int i = 0; i < len - 1; i++) {
					printNode(children.get(i), continuationIndent + " " + joint + horizontalLine, continuationIndent + ' ' + verticalLine + ' ');
				}
				printNode(children.getLast(), continuationIndent + " " + angle + horizontalLine, continuationIndent + "   ");
			}
			case null -> System.out.println(indent + "null");
			default -> throw new IllegalArgumentException(String.format("Unknown node: '%s'.", n));
		}
	}

	public static void main(final String[] args) {
		final ZombieParser parser = new ZombieParser();
		final Node ast = parser.parse("banana is a zombie summon remember 12 bind");

		printNode(ast, "", "");
	}
}
