package zint.lexer.token;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTokenPattern {

	@Test
	public void allTokens() {
		for(TokenType t : TokenType.values()) {
			assertTrue(Tokens.tokenMap.containsKey(t));
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
	public void entity() {
		List<String> examples = List.of("zombie", "ghost", "vampire", "demon", "djinn", "enslaved undead", "restless undead", "free-willed undead");
		List<String> counterExamples = examples.stream()
						.flatMap(s -> Stream.of("a"+s, s+"a", s.toUpperCase())).toList();
		checkExamples(TokenType.ENTITY, examples, counterExamples);
	}
}
