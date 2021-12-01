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

		for(TokenType t : List.of(TokenType.SUMMON, TokenType.ANIMATE, TokenType.DISTURB, TokenType.BIND, TokenType.TASK)) {
			examples = List.of(t.name().toLowerCase());
			counterExamples = List.of(t.name());
			checkExamples(t, examples, counterExamples);
		}

		examples = List.of("is a", "is an");
		counterExamples = List.of("IS A", "IS AN", "IS_A", "is is", "is ann");
		checkExamples(TokenType.IS_A, examples, counterExamples);
	}

	@Test
	public void taskStatements() {
		List<String> examples;
		List<String> counterExamples;

		for(TokenType t : List.of(TokenType.REMEMBER, TokenType.MOAN, TokenType.BANISH, TokenType.FORGET, TokenType.INVOKE, TokenType.SAY)) {
			examples = List.of(t.name().toLowerCase());
			counterExamples = List.of(t.name());
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
