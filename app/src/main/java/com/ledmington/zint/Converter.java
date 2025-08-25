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
import com.ledmington.zint.ast.Forget;
import com.ledmington.zint.ast.Instruction;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Remember;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Node;
import com.ledmington.zint.gen.ZombieParser.Terminal;
import com.ledmington.zint.gen.ZombieParser.entityDeclaration;
import com.ledmington.zint.gen.ZombieParser.instruction;
import com.ledmington.zint.gen.ZombieParser.prog;
import com.ledmington.zint.gen.ZombieParser.progbody;

public final class Converter {

	public static Program convertToAST(final ZombieParser.Node raw) {
		if (raw instanceof final prog p) {
			return convertProg(p);
		}
		throw new IllegalArgumentException("Could not convert given node.");
	}

	private static Program convertProg(final prog p) {
		return new Program(convertProgBody(p.progbody()));
	}

	private static List<EntityDeclaration> convertProgBody(final progbody pb) {
		return pb.entityDeclaration().stream()
				.map(Converter::convertEntityDeclaration)
				.toList();
	}

	private static EntityDeclaration convertEntityDeclaration(final entityDeclaration ed) {
		final Node decl = ed.or_0_0().match();
		final String name;
		final String entityTypeString;
		switch (decl) {
			case ZombieParser.sequence_0 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.ZOMBIE_3().literal();
			}
			case ZombieParser.sequence_1 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.ENSLAVED_UNDEAD_3().literal();
			}
			case ZombieParser.sequence_2 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.GHOST_3().literal();
			}
			case ZombieParser.sequence_3 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.RESTLESS_UNDEAD_3().literal();
			}
			case ZombieParser.sequence_4 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.VAMPIRE_3().literal();
			}
			case ZombieParser.sequence_5 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.FREE_WILLED_UNDEAD_3().literal();
			}
			case ZombieParser.sequence_6 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.DEMON_3().literal();
			}
			case ZombieParser.sequence_7 s -> {
				name = s.ID_0().literal();
				entityTypeString = s.DJINN_3().literal();
			}
			default -> throw new IllegalArgumentException(String.format("Unknown entity declaration: '%s'.", decl));
		}
		final EntityType entityType =
				switch (entityTypeString) {
					case "zombie", "enslaved undead" -> EntityType.ZOMBIE;
					case "ghost", "restless undead" -> EntityType.GHOST;
					case "vampire", "free-willed undead" -> EntityType.VAMPIRE;
					case "demon" -> EntityType.DEMON;
					case "djinn" -> EntityType.DJINN;
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
		return switch (n.match()) {
			case ZombieParser.sequence_13 s ->
				new Remember(Integer.parseInt(
						s.number_0().DIGIT().stream().map(Terminal::literal).collect(Collectors.joining())));
			case ZombieParser.Terminal ignored -> new Forget();
			default -> throw new IllegalArgumentException(String.format("Unknown instruction type: '%s'.", n.match()));
		};
	}
}
