package zint.lexer;

import org.junit.jupiter.api.*;
import zint.lexer.token.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestZombieLexer {

	private ZombieLexer lexer;

	@Test
	public void empty() {
		lexer = new ZombieLexer(new ByteArrayInputStream("".getBytes()));
		assertFalse(lexer.hasNext());
		assertThrows(IllegalStateException.class,
				() -> lexer.next());
	}

	@Test
	public void simple() {
		lexer = new ZombieLexer(new ByteArrayInputStream("hello\ntoken\n7".getBytes()));

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
}
