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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.ledmington.zint.ast.BodyType;
import com.ledmington.zint.ast.EntityBody;
import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Forget;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Remember;
import com.ledmington.zint.ast.Say;
import com.ledmington.zint.ast.Task;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.Node;

public final class TestConversion {

	private static Stream<Arguments> programs() {
		return Stream.of(
				Arguments.of(
						"banana is a zombie summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is an enslaved undead summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a ghost summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.GHOST,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a restless undead summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.GHOST,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a vampire summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.VAMPIRE,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a free-willed undead summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.VAMPIRE,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a demon summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.DEMON,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a djinn summon remember 12 bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.DJINN,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Remember(12))))))),
				Arguments.of(
						"banana is a zombie summon forget bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Forget())))))),
				Arguments.of(
						"banana is a zombie summon forget animate",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.SUMMON_ANIMATE, List.of(new Forget())))))),
				Arguments.of(
						"banana is a zombie summon forget disturb",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.SUMMON_DISTURB, List.of(new Forget())))))),
				Arguments.of(
						"banana is a zombie task example forget animate",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.TASK_ANIMATE, List.of(new Forget())))))),
				Arguments.of(
						"banana is a zombie task example forget bind",
						new Program(List.of(new EntityDeclaration(
								"banana",
								EntityType.ZOMBIE,
								new EntityBody(BodyType.TASK_BIND, List.of(new Forget())))))),
				Arguments.of(
						"example is a vampire summon forget example bind",
						new Program(List.of(new EntityDeclaration(
								"example",
								EntityType.VAMPIRE,
								new EntityBody(BodyType.SUMMON_BIND, List.of(new Forget(Optional.of("example")))))))),
				// from documentation
				Arguments.of(
						String.join(
								" ",
								"HelloWorld is a zombie",
								"summon",
								"task SayHello",
								"say \"Hello World!\"",
								"animate",
								"animate"),
						new Program(List.of(new EntityDeclaration(
								"HelloWorld",
								EntityType.ZOMBIE,
								new EntityBody(
										BodyType.SUMMON_ANIMATE,
										List.of(new Task("SayHello", List.of(new Say("Hello World!"))))))))),
				Arguments.of(
						String.join(
								" ",
								"Zombie1 is a zombie summon remember 1 bind",
								"Zombie2 is a zombie summon remember 2 bind",
								"example is a zombie summon",
								"remember Zombie1 moan Zombie1 moan Zombie2",
								"animate"),
						new Program(null)),
				Arguments.of(
						String.join(
								" ",
								"Zombie1 is a zombie",
								"summon remember 1 bind",
								"Zombie2 is a zombie",
								"summon remember 1 bind",
								"FibonacciZombie is a zombie",
								"summon",
								"remember 0",
								"task SayFibonaccis",
								"shamble",
								"say moan Zombie1",
								"say moan Zombie2",
								"remember Zombie1 moan Zombie1 moan Zombie2",
								"remember Zombie2 moan Zombie1 moan Zombie2",
								"remember moan 2",
								"until remembering 100",
								"animate",
								"animate"),
						new Program(null)));
	}

	@ParameterizedTest
	@MethodSource("programs")
	void parsing(final String input, final Program expected) {
		final ZombieParser parser = new ZombieParser();
		final Node parsed = parser.parse(input);
		assertNotNull(parsed, "Could not parse input.");
		final Program ast = Converter.convertToAST(parsed);
		assertEquals(expected, ast);
	}
}
