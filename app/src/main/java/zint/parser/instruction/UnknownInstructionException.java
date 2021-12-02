package zint.parser.instruction;

import zint.lexer.token.*;

import java.util.*;
import java.util.stream.*;

public class UnknownInstructionException extends NoSuchElementException {

	public UnknownInstructionException(final List<Token> tokens) {
		super("Unknown instruction for token sequence: \n" + tokens.stream()
				.map(t -> t.getType().name() + " (\"" + t.getValue() + "\")")
				.collect(Collectors.joining("\n")));
	}
}
