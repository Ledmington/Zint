package zint.lexer.token;

import java.util.*;
import java.util.regex.*;
import static java.util.Map.entry;

public class Tokens {

	public static final Map<TokenType, Pattern> tokenMap = Map.ofEntries(
			entry(TokenType.WHITESPACE, Pattern.compile("[\\s]")),
			entry(TokenType.ID,         Pattern.compile("[a-zA-Z][\\w]*")),
			entry(TokenType.NUMBER,     Pattern.compile("\\d+")),
			entry(TokenType.STRING,     Pattern.compile("\"[^\"]*\"")),
			entry(TokenType.ENTITY,     Pattern.compile("zombie|ghost|vampire|demon|djinn|enslaved undead|restless undead|free-willed undead"))
	);
}
