package zint.parser.instruction;

import org.junit.jupiter.api.*;
import zint.lexer.token.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTokenAcceptor {

	private TokenAcceptorBuilder tab;

	@BeforeEach
	public void setup() {
		tab = new TokenAcceptorBuilder();
	}

	@Test
	public void falseIfCantConsumeAllTokens() {
		TokenAcceptor ta = tab.one(TokenType.ID).build();
		assertFalse(ta.test(List.of(
				new Token(TokenType.ID),
				new Token(TokenType.ID)
		)));
	}

	@Test
	public void singleAnd() {
		TokenAcceptor ta = tab.one(TokenType.ID).build();
		assertFalse(ta.test(List.of()));
		assertTrue(ta.test(List.of(
				new Token(TokenType.ID)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.ID),
				new Token(TokenType.ID)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.SUMMON)
		)));
	}

	@Test
	public void doubleAnd() {
		TokenAcceptor ta = tab
				.one(TokenType.ID)
				.one(TokenType.SUMMON)
				.build();
		assertFalse(ta.test(List.of()));
		assertFalse(ta.test(List.of(
				new Token(TokenType.ID)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.SUMMON)
		)));
		assertTrue(ta.test(List.of(
				new Token(TokenType.ID),
				new Token(TokenType.SUMMON)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.SUMMON),
				new Token(TokenType.ID)
		)));
	}

	@Test
	public void singleAny() {
		TokenAcceptor ta = tab
				.any(TokenType.ID, TokenType.SUMMON)
				.build();
		assertFalse(ta.test(List.of()));
		assertTrue(ta.test(List.of(
				new Token(TokenType.ID)
		)));
		assertTrue(ta.test(List.of(
				new Token(TokenType.SUMMON)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.SUMMON),
				new Token(TokenType.ID)
		)));
	}
}
