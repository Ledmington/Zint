package zint.lexer.token;

import java.util.*;

public class Token {
	private final TokenType type;
	private final Optional<String> value;

	public Token(TokenType type, String value) {
		this.type = type;
		this.value = Optional.of(value);
	}

	public Token(TokenType type) {
		this.type = type;
		this.value = Optional.empty();
	}

	public TokenType getType() {
		return type;
	}

	public boolean hasValue() {
		return value.isPresent();
	}

	public String getValue() {
		return value.get();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Token token = (Token) o;
		return type == token.type && Objects.equals(value, token.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, value);
	}
}
