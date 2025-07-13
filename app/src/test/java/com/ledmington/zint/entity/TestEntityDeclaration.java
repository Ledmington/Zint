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
package com.ledmington.zint.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.ledmington.zint.Zint;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.ForgetNode;
import com.ledmington.zint.ast.InstructionNode;
import com.ledmington.zint.ast.ProgNode;
import com.ledmington.zint.ast.RememberNode;

public class TestEntityDeclaration {

	private static Stream<Arguments> allPossibleEntityDeclarations() {
		return Stream.of(
				Arguments.of(EntityType.ZOMBIE, "a zombie"),
				Arguments.of(EntityType.ZOMBIE, "an enslaved undead"),
				Arguments.of(EntityType.GHOST, "a ghost"),
				Arguments.of(EntityType.GHOST, "a restless undead"),
				Arguments.of(EntityType.VAMPIRE, "a vampire"),
				Arguments.of(EntityType.VAMPIRE, "a free-willed undead"),
				Arguments.of(EntityType.DEMON, "a demon"),
				Arguments.of(EntityType.DJINN, "a djinn"));
	}

	@ParameterizedTest
	@MethodSource("allPossibleEntityDeclarations")
	public void correctEntityType(final EntityType type, final String decl) {
		final ProgNode ast = (ProgNode) Zint.getAST("banana is " + decl);
		assertEquals(type, ast.getProgBody().getDeclarations().get(0).getType());
	}

	@ParameterizedTest
	@ValueSource(strings = {"b", "anEntity", "UppercaseIsAllowed", "a123"})
	public void correctID(final String name) {
		final ProgNode ast = (ProgNode) Zint.getAST(name + " is a zombie");
		assertEquals(name, ast.getProgBody().getDeclarations().get(0).getID().getName());
	}

	@Test
	public void multipleDeclarations() {
		final ProgNode ast = (ProgNode)
				Zint.getAST("""
				banana is a zombie
				Apple is a free-willed undead
				apple2 is a djinn
				""");

		assertEquals(3, ast.getProgBody().getDeclarations().size());

		assertEquals(
				EntityType.ZOMBIE, ast.getProgBody().getDeclarations().get(0).getType());
		assertEquals(
				"banana", ast.getProgBody().getDeclarations().get(0).getID().getName());

		assertEquals(
				EntityType.VAMPIRE, ast.getProgBody().getDeclarations().get(1).getType());
		assertEquals("Apple", ast.getProgBody().getDeclarations().get(1).getID().getName());

		assertEquals(
				EntityType.DJINN, ast.getProgBody().getDeclarations().get(2).getType());
		assertEquals(
				"apple2", ast.getProgBody().getDeclarations().get(2).getID().getName());
	}

	@Test
	public void simpleRemember() {
		final ProgNode ast =
				(ProgNode) Zint.getAST("""
				banana is a zombie
				summon
						remember 12
				bind
				""");

		final List<InstructionNode> instructions =
				ast.getProgBody().getDeclarations().get(0).getInstructions();
		assertEquals(1, instructions.size());
		assertTrue(instructions.get(0) instanceof RememberNode);
		assertEquals(12, ((RememberNode) instructions.get(0)).getValue());
	}

	@Test
	public void simpleForget() {
		final ProgNode ast =
				(ProgNode) Zint.getAST("""
				banana is a zombie
				summon
						forget
				bind
				""");

		final List<InstructionNode> instructions =
				ast.getProgBody().getDeclarations().get(0).getInstructions();
		assertEquals(1, instructions.size());
		assertTrue(instructions.get(0) instanceof ForgetNode);
	}
}
