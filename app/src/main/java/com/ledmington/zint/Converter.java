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

import java.util.List;
import java.util.stream.Collectors;

import com.ledmington.zint.ast.BodyType;
import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Instruction;
import com.ledmington.zint.ast.Node;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Remember;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Terminal;
import com.ledmington.zint.gen.ZombieParser.entityDeclaration;
import com.ledmington.zint.gen.ZombieParser.instruction;
import com.ledmington.zint.gen.ZombieParser.prog;
import com.ledmington.zint.gen.ZombieParser.progbody;
import com.ledmington.zint.gen.ZombieParser.sequence_13;

public final class Converter {

	public static Node convertToAST(final ZombieParser.Node raw) {
		if (raw instanceof final prog p) {
			return convertProg(p);
		}
		throw new IllegalArgumentException("Could not convert given node.");
	}

	private static Node convertProg(final prog p) {
		return new Program(convertProgBody(p.progbody()));
	}

	private static List<EntityDeclaration> convertProgBody(final progbody pb) {
		return pb.entityDeclaration().stream()
				.map(Converter::convertEntityDeclaration)
				.toList();
	}

	private static EntityDeclaration convertEntityDeclaration(final entityDeclaration ed) {
		final String name =
				((ZombieParser.sequence_0) ed.or_0_0().match()).ID_0().literal();
		final String entityTypeString =
				((ZombieParser.sequence_0) ed.or_0_0().match()).ZOMBIE_3().literal();
		final EntityType entityType =
				switch (entityTypeString) {
					case "zombie" -> EntityType.ZOMBIE;
					default ->
						throw new IllegalArgumentException(
								String.format("Unknown entity type: '%s'.", entityTypeString));
				};
		final String bodyTypeString = ((ZombieParser.sequence_9) ed.or_1_0().match())
						.SUMMON_0()
						.literal() + " "
				+ ((ZombieParser.sequence_9) ed.or_1_0().match()).BIND_1().literal();
		final BodyType bodyType =
				switch (bodyTypeString) {
					case "summon animate" -> BodyType.SUMMON_ANIMATE;
					case "summon bind" -> BodyType.SUMMON_BIND;
					default ->
						throw new IllegalArgumentException(String.format("Unknown body type: '%s'.", bodyTypeString));
				};
		final List<Instruction> instructions = ((ZombieParser.sequence_9)
						ed.or_1_0().match())
				.one_or_more_1_0().instruction().stream()
						.map(Converter::convertInstruction)
						.toList();
		return new EntityDeclaration(name, entityType, bodyType, instructions);
	}

	private static Instruction convertInstruction(final instruction n) {
		final sequence_13 inst = ((sequence_13) n.match());
		return switch (inst.REMEMBER_0().literal()) {
			case "remember" ->
				new Remember(Integer.parseInt(
						inst.number_0().DIGIT().stream().map(Terminal::literal).collect(Collectors.joining())));
			default -> throw new IllegalArgumentException(String.format("Unknown instruction type: '%s'.", inst));
		};
	}
}
