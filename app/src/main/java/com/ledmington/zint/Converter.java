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
import com.ledmington.zint.ast.EntityBody;
import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Forget;
import com.ledmington.zint.ast.Instruction;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Remember;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Node;
import com.ledmington.zint.gen.ZombieParser.Terminal;
import com.ledmington.zint.gen.ZombieParser.entityBody;
import com.ledmington.zint.gen.ZombieParser.entityDeclaration;
import com.ledmington.zint.gen.ZombieParser.instruction;
import com.ledmington.zint.gen.ZombieParser.prog;
import com.ledmington.zint.gen.ZombieParser.sequence_10;
import com.ledmington.zint.gen.ZombieParser.sequence_11;
import com.ledmington.zint.gen.ZombieParser.sequence_12;
import com.ledmington.zint.gen.ZombieParser.sequence_13;
import com.ledmington.zint.gen.ZombieParser.sequence_5;
import com.ledmington.zint.gen.ZombieParser.sequence_6;
import com.ledmington.zint.gen.ZombieParser.sequence_7;
import com.ledmington.zint.gen.ZombieParser.sequence_8;
import com.ledmington.zint.gen.ZombieParser.sequence_9;

public final class Converter {

	public static Program convertToAST(final Node raw) {
		if (raw instanceof final prog p) {
			return convertProg(p);
		}
		throw new IllegalArgumentException("Could not convert given node.");
	}

	private static Program convertProg(final prog p) {
		return new Program(p.entityDeclaration().stream()
				.map(Converter::convertEntityDeclaration)
				.toList());
	}

	private static EntityDeclaration convertEntityDeclaration(final entityDeclaration ed) {
		final String name = ed.entityNameAndType_0().ID_0().literal();
		final String entityTypeString = getEntityTypeString(ed);
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

		return new EntityDeclaration(name, entityType, convertEntityBody(ed.entityBody_0()));
	}

	private static String getEntityTypeString(final entityDeclaration ed) {
		final Node decl = ed.entityNameAndType_0().or_0_0().match();
		return switch (decl) {
			case sequence_5 s -> s.ZOMBIE_1().literal();
			case sequence_6 s -> s.ENSLAVED_UNDEAD_1().literal();
			case sequence_7 s -> s.GHOST_1().literal();
			case sequence_8 s -> s.RESTLESS_UNDEAD_1().literal();
			case sequence_9 s -> s.VAMPIRE_1().literal();
			case sequence_10 s -> s.FREE_WILLED_UNDEAD_1().literal();
			case sequence_11 s -> s.DEMON_1().literal();
			case sequence_12 s -> s.DJINN_1().literal();
			default -> throw new IllegalArgumentException(String.format("Unknown entity declaration: '%s'.", decl));
		};
	}

	private static EntityBody convertEntityBody(final entityBody entityBody) {
		final BodyType type;
		final List<instruction> inst;
		switch (entityBody.match()) {
			case ZombieParser.sequence_0 s -> {
				type = BodyType.SUMMON_ANIMATE;
				inst = s.one_or_more_0_0().instruction();
			}
			case ZombieParser.sequence_1 s -> {
				type = BodyType.SUMMON_BIND;
				inst = s.one_or_more_1_0().instruction();
			}
			case ZombieParser.sequence_2 s -> {
				type = BodyType.SUMMON_DISTURB;
				inst = s.one_or_more_2_0().instruction();
			}
			case ZombieParser.sequence_3 s -> {
				type = BodyType.TASK_ANIMATE;
				inst = s.one_or_more_3_0().instruction();
			}
			case ZombieParser.sequence_4 s -> {
				type = BodyType.TASK_BIND;
				inst = s.one_or_more_4_0().instruction();
			}
			default -> throw new IllegalStateException(String.format("Unknown body type: '%s'.", entityBody.match()));
		}
		final List<Instruction> instructions =
				inst.stream().map(Converter::convertInstruction).toList();
		return new EntityBody(type, instructions);
	}

	private static Instruction convertInstruction(final instruction n) {
		return switch (n.match()) {
			case sequence_13 s ->
				new Remember(Integer.parseInt(
						s.number_0().DIGIT().stream().map(Terminal::literal).collect(Collectors.joining())));
			case Terminal ignored -> new Forget();
			default -> throw new IllegalArgumentException(String.format("Unknown instruction type: '%s'.", n.match()));
		};
	}
}
