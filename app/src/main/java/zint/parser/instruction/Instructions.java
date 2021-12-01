package zint.parser.instruction;

import zint.lexer.token.*;

import java.util.*;

import static java.util.Map.*;

public class Instructions {

	private final Map<InstructionType, TokenAcceptor> instructionMap = Map.ofEntries(
		entry(InstructionType.ENTITY_DECLARATION, new TokenAcceptorBuilder()
				.one(TokenType.ID)
				.oneOrMore(TokenType.WHITESPACE)
				.one(TokenType.IS_A)
				.oneOrMore(TokenType.WHITESPACE)
				.one(TokenType.ENTITY)
				.build())
	);
}
