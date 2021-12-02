package zint.lexer.token;

import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import static java.util.Map.entry;

public class Tokens {

	public static final Map<TokenType, Pattern> tokenMap = Map.ofEntries(
			// Basic tokens
			entry(TokenType.WHITESPACE, Pattern.compile("[\\s]")),
			entry(TokenType.ID,         Pattern.compile("[a-zA-Z][\\w]*")),
			entry(TokenType.NUMBER,     Pattern.compile("\\d+")),
			entry(TokenType.STRING,     Pattern.compile("\"[^\"]*\"")),
			entry(TokenType.ENTITY,     Pattern.compile("zombie|ghost|vampire|demon|djinn|enslaved undead|restless undead|free-willed undead")),

			// Entity declarations
			exactMatch(TokenType.SUMMON),
			exactMatch(TokenType.ANIMATE),
			exactMatch(TokenType.BIND),
			exactMatch(TokenType.DISTURB),
			exactMatch(TokenType.TASK),
			exactMatch(TokenType.IS),
			entry(TokenType.ARTICLE, Pattern.compile("a|an")),

			// Task statements
			exactMatch(TokenType.REMEMBER),
			exactMatch(TokenType.MOAN),
			exactMatch(TokenType.BANISH),
			exactMatch(TokenType.FORGET),
			exactMatch(TokenType.INVOKE),
			exactMatch(TokenType.SAY),

			// Flow control
			exactMatch(TokenType.SHAMBLE),
			exactMatch(TokenType.UNTIL),
			exactMatch(TokenType.AROUND),
			exactMatch(TokenType.STUMBLE),
			exactMatch(TokenType.TASTE),
			exactMatch(TokenType.GOOD),
			exactMatch(TokenType.BAD),
			exactMatch(TokenType.SPIT),

			// Operators
			exactMatch(TokenType.REMEMBERING),
			exactMatch(TokenType.REND),
			exactMatch(TokenType.TURN)

	);

	private Tokens() {}

	private static Entry<TokenType, Pattern> exactMatch(TokenType t) {
		return entry(t, Pattern.compile(t.name().toLowerCase()));
	}
}
