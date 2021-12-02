package zint.lexer;

import org.junit.jupiter.api.*;
import zint.lexer.token.*;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestZombieLexer {

	private ZombieLexer lexer;

	private void buildLexer(final String code) {
		lexer = new ZombieLexer(new ByteArrayInputStream(code.getBytes()));
	}

	@Test
	public void empty() {
		buildLexer("");
		assertFalse(lexer.hasNext());
		assertThrows(IllegalStateException.class,
				() -> lexer.next());
	}

	@Test
	public void simple() {
		buildLexer("hello\ntoken\n7");

		assertTrue(lexer.hasNext());
		assertEquals(lexer.next().getType(), TokenType.ID);

		assertTrue(lexer.hasNext());
		assertEquals(lexer.next().getType(), TokenType.WHITESPACE);

		assertTrue(lexer.hasNext());
		assertEquals(lexer.next().getType(), TokenType.ID);

		assertTrue(lexer.hasNext());
		assertEquals(lexer.next().getType(), TokenType.WHITESPACE);

		assertTrue(lexer.hasNext());
		assertEquals(lexer.next().getType(), TokenType.NUMBER);

		assertFalse(lexer.hasNext());
	}

	@Test
	public void unknownCharacterTokens() {
		for(char c : ";:-\\|!£$%&/()=?'^*[]{}#@€<>àèéìòù_§°ç+.,~`".toCharArray()) {
			buildLexer(String.valueOf(c));
			assertTrue(lexer.hasNext());
			assertThrows(NoSuchTokenException.class, () -> lexer.next());
		}
	}

	@Test
	public void checkIsAgainstID() {
		buildLexer("is");
		Token t = lexer.next();
		assertEquals(TokenType.IS, t.getType());
		assertEquals(t.getValue(), "is");
	}

	@Test
	public void checkLastCharacterIsNotDiscarded() {
		Tokens.tokenMap.entrySet().stream()
				// Getting only the "exact match" tokens
				.filter(e -> e.getKey().name().toLowerCase().equals(e.getValue().pattern()))
				.map(Map.Entry::getKey)
				.forEach(type -> {
					buildLexer(type.name().toLowerCase());
					Token token = lexer.next();
					assertEquals(type, token.getType(), "TokenType \"" + type.name() + "\" is not matched correctly");
					assertEquals(type.name().toLowerCase(), token.getValue());
				});
	}
}
