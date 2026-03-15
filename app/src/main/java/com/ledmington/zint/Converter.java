/*
 * Zint - ZOMBIE interpreter
 * Copyright (C) 2022-2026 Filippo Barbari <filippo.barbari@gmail.com>
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
import com.ledmington.zint.ast.EntityStatementAtom;
import com.ledmington.zint.ast.EntityStatementControl;
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
			case ZombieParser.entity_statement_atom a -> convertEntityStatementAtom(a);
			case ZombieParser.entity_statement_control c -> convertEntityStatementControl(c);
			default -> throw new IllegalArgumentException(String.format("Unknown entity statement: '%s'.", es));
		};
	}

	private static EntityStatementAtom convertEntityStatementAtom(final ZombieParser.entity_statement_atom a) {
		return switch (a.match()) {
			case ZombieParser.sequence_3 s ->
				new Forget(getOptional(s.zero_or_one_3().ID()));
			case ZombieParser.sequence_6 s ->
				new Remember(Integer.parseInt(s.NUMBER().literal()));
			default -> throw new IllegalArgumentException(String.format("Unknown entity statement atom: '%s'.", a));
		};
	}

	private static EntityStatementControl convertEntityStatementControl(final ZombieParser.entity_statement_control c) {
		return switch (c.match()) {
			case ZombieParser.summon s -> {
				// summon
				final List<EntityStatementAtom> les = s.entity_block().entity_statement_atom().stream()
						.map(Converter::convertEntityStatementAtom)
						.toList();
				final Node summonType = s.or_1().match();
				yield switch (summonType) {
					case ZombieParser.Terminal t when t.literal().equals("bind") -> new SummonBind(les);
					default ->
						throw new IllegalArgumentException(String.format("Unknown summon type: '%s'.", summonType));
				};
			}
			default -> throw new IllegalArgumentException(String.format("Unknown entity statement control: '%s'.", c));
		};
	}

	private static Optional<String> getOptional(final Terminal t) {
		return t == null ? Optional.empty() : Optional.of(t.literal());
	}
}
