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

	@Test
	public void doubleAny() {
		TokenAcceptor ta = tab
				.any(TokenType.ID, TokenType.SUMMON)
				.any(TokenType.ANIMATE, TokenType.BANISH)
				.build();

		assertTrue(ta.test(List.of(
				new Token(TokenType.ID),
				new Token(TokenType.ANIMATE)
		)));
		assertTrue(ta.test(List.of(
				new Token(TokenType.ID),
				new Token(TokenType.BANISH)
		)));
		assertTrue(ta.test(List.of(
				new Token(TokenType.SUMMON),
				new Token(TokenType.ANIMATE)
		)));
		assertTrue(ta.test(List.of(
				new Token(TokenType.SUMMON),
				new Token(TokenType.BANISH)
		)));
	}

	@Test
	public void singleZeroOrMore() {
		TokenAcceptor ta = tab
				.zeroOrMore(TokenType.ID)
				.build();

		for(int i=0; i<10; i++) {
			assertTrue(ta.test(Collections.nCopies(i, new Token(TokenType.ID))));
		}

		// ZeroOrMore returns always true, but this test fails because
		// some steps in the pipeline didn't complete successfully
		assertFalse(ta.test(List.of(
				new Token(TokenType.BAD)
		)));
	}

	@Test
	public void doubleZeroOrMore() {
		TokenAcceptor ta = tab
				.zeroOrMore(TokenType.ID)
				.zeroOrMore(TokenType.ANIMATE)
				.build();

		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				List<Token> l = new LinkedList<>();
				l.addAll(Collections.nCopies(i, new Token(TokenType.ID))) ;
				l.addAll(Collections.nCopies(j, new Token(TokenType.ANIMATE)));
				assertTrue(ta.test(l));
			}
		}
	}

	@Test
	public void singleOneOrMore() {
		TokenAcceptor ta = tab.oneOrMore(TokenType.ID).build();

		assertFalse(ta.test(List.of()));
		assertFalse(ta.test(List.of(
				new Token(TokenType.SUMMON)
		)));

		for(int i=1; i<10; i++) {
			assertTrue(ta.test(Collections.nCopies(i, new Token(TokenType.ID))));
		}
	}

	@Test
	public void doubleOneOrMore() {
		TokenAcceptor ta = tab
				.oneOrMore(TokenType.ID)
				.oneOrMore(TokenType.SUMMON)
				.build();

		assertFalse(ta.test(List.of()));
		assertFalse(ta.test(List.of(
				new Token(TokenType.ID)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.SUMMON)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.ANIMATE),
				new Token(TokenType.SUMMON)
		)));
		assertFalse(ta.test(List.of(
				new Token(TokenType.ID),
				new Token(TokenType.ANIMATE)
		)));

		for(int i=1; i<10; i++) {
			for(int j=1; j<10; j++) {
				List<Token> l = new LinkedList<>();
				l.addAll(Collections.nCopies(i, new Token(TokenType.ID)));
				l.addAll(Collections.nCopies(j, new Token(TokenType.SUMMON)));
				assertTrue(ta.test(l));
			}
		}
	}
}
