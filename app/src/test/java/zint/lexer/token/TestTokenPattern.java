package zint.lexer.token;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenPattern {

	private static final Map<TokenType, Integer> tokenCounterMap = new EnumMap<>(TokenType.class);

	@BeforeAll
	public static void allTokens() {
		for(TokenType t : TokenType.values()) {
			assertTrue(Tokens.tokenMap.containsKey(t), "Token " + t.name() + " is not in the TokensMap");
			tokenCounterMap.put(t, 0);
		}
	}

	private void checkExamples(TokenType type, List<String> examples, List<String> counterExamples) {
		var pattern = Tokens.tokenMap.get(type);
		for(String example : examples) {
			assertTrue(pattern.matcher(example).matches(),
					"\"" + example + "\" does not match \"" + pattern.pattern() + "\" for token " + type.name());
		}
		for(String example : counterExamples) {
			assertFalse(pattern.matcher(example).matches(),
					"\"" + example + "\" matches \"" + pattern.pattern() + "\" for token " + type.name());
		}
		tokenCounterMap.put(type, tokenCounterMap.get(type)+1);
	}

	@Test
	public void whitespace() {
		List<String> examples = List.of(" ", "\t", "\n", "\r");
		List<String> counterExamples = List.of("a", "xxx", "123", "ABC");
		checkExamples(TokenType.WHITESPACE, examples, counterExamples);
	}

	@Test
	public void number() {
		List<String> examples = List.of("0", "12", "00000", "981734891");
		List<String> counterExamples = List.of("a12", "12a", "x", "x y");
		checkExamples(TokenType.NUMBER, examples, counterExamples);
	}

	@Test
	public void id() {
		List<String> examples = List.of("abc", "zombie", "Var", "FOO", "task12", "a1b2c3");
		List<String> counterExamples = List.of("0", "123456", "2a", "a a");
		checkExamples(TokenType.ID, examples, counterExamples);
	}

	@Test
	public void string() {
		List<String> examples = Stream.of("abc", "zombie", "Var", "FOO", "task12", "a1b2c3", "hello world", "").map(s -> "\""+s+"\"").toList();
		List<String> counterExamples = Stream.of("abc", "zombie", "Var", "FOO", "task12", "a1b2c3", "hello world")
						.flatMap(s -> Stream.of("\""+s, s+"\"", "'"+s, s+"'", "'"+s+"'")).toList();
		checkExamples(TokenType.STRING, examples, counterExamples);
	}

	@Test
	public void entity() {
		List<String> examples = List.of("zombie", "ghost", "vampire", "demon", "djinn", "enslaved undead", "restless undead", "free-willed undead");
		List<String> counterExamples = examples.stream()
						.flatMap(s -> Stream.of("a"+s, s+"a", s.toUpperCase())).toList();
		checkExamples(TokenType.ENTITY, examples, counterExamples);
	}

	@Test
	public void entityDeclarations() {
		List<String> examples;
		List<String> counterExamples;
		List<TokenType> tokensToTest = List.of(
				TokenType.SUMMON,
				TokenType.ANIMATE,
				TokenType.DISTURB,
				TokenType.BIND,
				TokenType.TASK,
				TokenType.IS);

		for(TokenType t : tokensToTest) {
			String name = t.name();
			String lowerName = name.toLowerCase();
			examples = List.of(lowerName);
			counterExamples = List.of(name, " "+lowerName, lowerName+" ");
			checkExamples(t, examples, counterExamples);
		}

		examples = List.of("a", "an");
		counterExamples = List.of("A", "AN", "A", "is is", "ann");
		checkExamples(TokenType.ARTICLE, examples, counterExamples);
	}

	@Test
	public void taskStatements() {
		List<String> examples;
		List<String> counterExamples;
		List<TokenType> tokensToTest = List.of(
				TokenType.REMEMBER,
				TokenType.MOAN,
				TokenType.BANISH,
				TokenType.FORGET,
				TokenType.INVOKE,
				TokenType.SAY);

		for(TokenType t : tokensToTest) {
			String name = t.name();
			String lowerName = name.toLowerCase();
			examples = List.of(lowerName);
			counterExamples = List.of(name, " "+lowerName, lowerName+" ");
			checkExamples(t, examples, counterExamples);
		}
	}

	@Test
	public void flowControl() {
		List<String> examples;
		List<String> counterExamples;
		List<TokenType> tokensToTest = List.of(
				TokenType.SHAMBLE,
				TokenType.UNTIL,
				TokenType.AROUND,
				TokenType.STUMBLE,
				TokenType.TASTE,
				TokenType.GOOD,
				TokenType.BAD,
				TokenType.SPIT);

		for(TokenType t : tokensToTest) {
			String name = t.name();
			String lowerName = name.toLowerCase();
			examples = List.of(lowerName);
			counterExamples = List.of(name, " "+lowerName, lowerName+" ");
			checkExamples(t, examples, counterExamples);
		}
	}

	@Test
	public void operators() {
		List<String> examples;
		List<String> counterExamples;
		List<TokenType> tokensToTest = List.of(
				TokenType.REMEMBERING,
				TokenType.REND,
				TokenType.TURN);

		for(TokenType t : tokensToTest) {
			String name = t.name();
			String lowerName = name.toLowerCase();
			examples = List.of(lowerName);
			counterExamples = List.of(name, " "+lowerName, lowerName+" ");
			checkExamples(t, examples, counterExamples);
		}
	}

	@AfterAll
	public static void checkEachTokenIsTested() {
		for(TokenType t : tokenCounterMap.keySet()) {
			assertTrue(tokenCounterMap.get(t) > 0, "Token " + t.name() + " has not been tested");
		}
	}
}
