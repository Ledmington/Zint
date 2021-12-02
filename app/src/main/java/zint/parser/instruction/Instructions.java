package zint.parser.instruction;

import zint.lexer.token.*;

import java.util.*;

import static java.util.Map.*;

public class Instructions {

	public static final Map<InstructionType, TokenAcceptor> instructionMap = Map.ofEntries(
		entry(InstructionType.ENTITY_DECLARATION, new TokenAcceptorBuilder()
				.one(TokenType.ID)
				.oneOrMore(TokenType.WHITESPACE)
				.one(TokenType.IS)
				.oneOrMore(TokenType.WHITESPACE)
				.one(TokenType.ARTICLE)
				.oneOrMore(TokenType.WHITESPACE)
				.one(TokenType.ENTITY)
				.build())
	);

	private Instructions() {}
}
