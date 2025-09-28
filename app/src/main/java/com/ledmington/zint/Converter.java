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
import java.util.Optional;

import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityStatement;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Forget;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Remember;
import com.ledmington.zint.ast.SummonBind;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Node;
import com.ledmington.zint.gen.ZombieParser.Terminal;
import com.ledmington.zint.gen.ZombieParser.prog;

public final class Converter {

	public static Program convertToAST(final Node raw) {
		if (raw instanceof final prog p) {
			return convertProg(p);
		}
		throw new IllegalArgumentException("Could not convert given node.");
	}

	private static Program convertProg(final prog p) {
		return new Program(p.entity_declaration().stream()
				.map(Converter::convertEntityDeclaration)
				.toList());
	}

	private static EntityDeclaration convertEntityDeclaration(final ZombieParser.entity_declaration ed) {
		final String name = ed.entity_name_and_type().ID().literal();
		final Node entityTypeNode = ed.entity_name_and_type().or_0().match();
		final EntityType entityType =
				switch (entityTypeNode) {
					case ZombieParser.zombie ignored -> EntityType.ZOMBIE;
					case ZombieParser.ghost ignored -> EntityType.GHOST;
					case ZombieParser.vampire ignored -> EntityType.VAMPIRE;
					case ZombieParser.demon ignored -> EntityType.DEMON;
					case ZombieParser.djinn ignored -> EntityType.DJINN;
					default ->
						throw new IllegalArgumentException(String.format("Unknown entity type: '%s'.", entityTypeNode));
				};

		return new EntityDeclaration(
				name,
				entityType,
				ed.one_or_more_0().entity_statement().stream()
						.map(Converter::convertEntityStatement)
						.toList());
	}

	private static EntityStatement convertEntityStatement(final ZombieParser.entity_statement es) {
		return switch (es.match()) {
			case ZombieParser.forget f ->
				new Forget(getOptional(f.zero_or_one_8().ID()));
			case ZombieParser.remember r ->
				new Remember(Integer.parseInt(r.NUMBER().literal()));
			case ZombieParser.summon s -> {
				final List<EntityStatement> statements = s.one_or_more_7().entity_statement().stream()
						.map(Converter::convertEntityStatement)
						.toList();
				final Node summonType = s.or_1().match();
				yield switch (summonType) {
					case ZombieParser.Terminal t when t.literal().equals("bind") -> new SummonBind(statements);
					default ->
						throw new IllegalArgumentException(String.format("Unknown summon type: '%s'.", summonType));
				};
			}

			default -> throw new IllegalArgumentException(String.format("Unknown entity statement: '%s'.", es));
		};
	}

	private static Optional<String> getOptional(final Terminal t) {
		return t == null ? Optional.empty() : Optional.of(t.literal());
	}

	/*


		private static EntityBody convertEntityBody(final entity_body entityBody) {
			final BodyType type;
			final List<instruction> inst;
			switch (entityBody.match()) {
				case ZombieParser.sequence_0 s -> {
					type = BodyType.SUMMON_ANIMATE;
					inst = s.one_or_more_0().instruction();
				}
				case ZombieParser.sequence_1 s -> {
					type = BodyType.SUMMON_BIND;
					inst = s.one_or_more_1().instruction();
				}
				case ZombieParser.sequence_2 s -> {
					type = BodyType.SUMMON_DISTURB;
					inst = s.one_or_more_2().instruction();
				}
				case ZombieParser.sequence_3 s -> {
					type = BodyType.TASK_ANIMATE;
					inst = s.one_or_more_3().instruction();
				}
				case ZombieParser.sequence_4 s -> {
					type = BodyType.TASK_BIND;
					inst = s.one_or_more_4().instruction();
				}
				default -> throw new IllegalStateException(String.format("Unknown body type: '%s'.", entityBody.match()));
			}
			final List<Instruction> instructions =
					inst.stream().map(Converter::convertInstruction).toList();
			return new EntityBody(type, instructions);
		}

		private static Instruction convertInstruction(final instruction n) {
			return switch (n.match()) {
				case sequence_16 s16 -> {
					final Terminal id = s16.zero_or_one_3().ID();
					yield new Forget(id == null ? Optional.empty() : Optional.of(id.literal()));
				}
				case sequence_19 s19 -> new Remember(Integer.parseInt(s19.NUMBER().literal()));
				default -> throw new IllegalArgumentException(String.format("Unknown instruction type: '%s'.", n.match()));
			};
		}
	*/
	private static String trimDoubleQuotes(final String literal) {
		final int n = literal.length();
		if (literal.charAt(0) != '"' || literal.charAt(n - 1) != '"') {
			throw new AssertionError();
		}
		return literal.substring(1, n - 1);
	}
}
