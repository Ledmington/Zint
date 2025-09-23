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
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.ast.Task;
import com.ledmington.zint.exc.DuplicateEntityName;
import com.ledmington.zint.exc.DuplicateTaskName;

public final class Checker {
	private Checker() {}

	public static void check(final Program ast) {
		checkDuplicateEntityNames(ast);
		checkDuplicateTaskNames(ast);
	}

	private static void checkDuplicateEntityNames(final Program ast) {
		final int n = ast.declarations().size();
		for (int i = 0; i < n; i++) {
			final String id = ast.declarations().get(i).name();
			for (int j = i + 1; j < n; j++) {
				if (id.equals(ast.declarations().get(j).name())) {
					throw new DuplicateEntityName(id);
				}
			}
		}
	}

	private static void checkDuplicateTaskNames(final Program ast) {
		for (final EntityDeclaration decl : ast.declarations()) {
			final List<Task> taskDeclarations = decl.body().instructions().stream()
					.filter(inst -> inst instanceof Task)
					.map(inst -> (Task) inst)
					.toList();
			final int n = taskDeclarations.size();
			for (int i = 0; i < n; i++) {
				final String taskID = taskDeclarations.get(i).name();
				for (int j = i + 1; j < n; j++) {
					if (taskID.equals(taskDeclarations.get(j).name())) {
						throw new DuplicateTaskName(decl.name(), taskID);
					}
				}
			}
		}
	}
}
