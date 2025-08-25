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

import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Node;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.entityDeclaration;
import com.ledmington.zint.gen.ZombieParser.prog;
import com.ledmington.zint.gen.ZombieParser.progbody;

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
		return new EntityDeclaration(
				((ZombieParser.sequence_0) ed.or_0_0().match()).ID_0().literal(),
				switch (((ZombieParser.sequence_0) ed.or_0_0().match())
						.ZOMBIE_3()
						.literal()) {
					case "zombie" -> EntityType.ZOMBIE;
					default -> throw new IllegalArgumentException("Unknown entity type.");
				});
	}
}
