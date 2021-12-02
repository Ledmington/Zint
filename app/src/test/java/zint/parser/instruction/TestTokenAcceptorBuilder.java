package zint.parser.instruction;

import org.junit.jupiter.api.*;
import zint.lexer.token.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTokenAcceptorBuilder {

	private TokenAcceptorBuilder tab;

	@BeforeEach
	public void setup() {
		tab = new TokenAcceptorBuilder();
	}

	@Test
	public void empty() {
		assertThrows(IllegalStateException.class, () -> tab.build());
	}

	@Test
	public void wrongAny() {
		assertThrows(IllegalArgumentException.class, () -> tab.any());
		assertThrows(IllegalArgumentException.class, () -> tab.any(TokenType.ID));
		assertThrows(IllegalArgumentException.class, () -> tab.any(TokenType.ID, TokenType.ID));
		assertDoesNotThrow(() -> tab.any(TokenType.ID, TokenType.ANIMATE));
	}

	@Test
	public void noNulls() {
		assertThrows(NullPointerException.class, () -> tab.one(null));
		assertThrows(NullPointerException.class, () -> tab.any((TokenType[]) null));
		assertThrows(NullPointerException.class, () -> tab.zeroOrMore(null));
	}
}
