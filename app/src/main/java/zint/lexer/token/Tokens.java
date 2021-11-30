package zint.lexer.token;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import static java.util.Map.entry;

public class Tokens {

	public static final Map<TokenType, Pattern> tokenMap = Map.ofEntries(
			entry(TokenType.WHITESPACE, Pattern.compile("[\\s]")),
			entry(TokenType.ID,         Pattern.compile("[a-zA-Z][\\w]*")),
			entry(TokenType.NUMBER,     Pattern.compile("\\d+")),
			entry(TokenType.STRING,     Pattern.compile("\"[^\"]*\"")),
			entry(TokenType.ENTITY,     Pattern.compile("zombie|ghost|vampire|demon|djinn|enslaved undead|restless undead|free-willed undead")),

			simplePattern(TokenType.SUMMON),
			simplePattern(TokenType.ANIMATE),
			simplePattern(TokenType.BIND),
			simplePattern(TokenType.DISTURB),
			simplePattern(TokenType.TASK),
			entry(TokenType.IS_A, Pattern.compile("is a|is an"))
	);

	private static Entry<TokenType, Pattern> simplePattern(TokenType t) {
		return entry(t, Pattern.compile(t.name().toLowerCase()));
	}
}
