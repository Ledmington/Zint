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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ledmington.zint.ast.Program;
import com.ledmington.zint.exc.DuplicateEntityName;
import com.ledmington.zint.exc.DuplicateTaskName;
import com.ledmington.zint.gen.ZombieParser;

public final class TestChecker {

	@Test
	void duplicateEntityName() {
		final String code = "example is a zombie summon forget bind example is a zombie summon forget bind";
		final ZombieParser parser = new ZombieParser();
		final com.ledmington.zint.gen.ZombieParser.Node raw = parser.parse(code);
		assertNotNull(raw);
		final Program ast = Converter.convertToAST(raw);
		assertThrows(DuplicateEntityName.class, () -> Checker.check(ast));
	}

	@Test
	void duplicateTaskName() {
		final String code =
				"example is a zombie summon task exampleTask forget animate task exampleTask forget animate bind";
		final ZombieParser parser = new ZombieParser();
		final com.ledmington.zint.gen.ZombieParser.Node raw = parser.parse(code);
		assertNotNull(raw);
		final Program ast = Converter.convertToAST(raw);
		assertThrows(DuplicateTaskName.class, () -> Checker.check(ast));
	}
}
