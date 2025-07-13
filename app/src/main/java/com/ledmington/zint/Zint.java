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

public final class Zint {
	public static void main(final String[] args) {
		final ZombieParser parser = new ZombieParser();
		final Node ast = parser.parse("banana is a zombie summon remember 12 bind");
		System.out.println(ast);
	}
}
