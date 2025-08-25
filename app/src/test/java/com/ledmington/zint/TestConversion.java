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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ledmington.zint.ast.BodyType;
import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Remember;
import com.ledmington.zint.gen.ZombieParser;

public final class TestConversion {

	private static Stream<Arguments> programs() {
		return Stream.of(
				Arguments.of(
						"banana is a zombie summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.ZOMBIE, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is an enslaved undead summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.ZOMBIE, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is a ghost summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.GHOST, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is a restless undead summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.GHOST, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is a vampire summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.VAMPIRE, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is a free-willed undead summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.VAMPIRE, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is a demon summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.DEMON, BodyType.SUMMON_BIND, List.of(new Remember(12)))))),
				Arguments.of(
						"banana is a djinn summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana", EntityType.DJINN, BodyType.SUMMON_BIND, List.of(new Remember(12)))))));
	}

	@ParameterizedTest
	@MethodSource("programs")
	void parsing(final String input, final Program expected) {
		final ZombieParser parser = new ZombieParser();
		final Program ast = Converter.convertToAST(parser.parse(input));
		assertEquals(expected, ast);
	}
}
