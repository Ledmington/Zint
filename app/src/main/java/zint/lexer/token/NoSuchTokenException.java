package zint.lexer.token;

import java.util.*;

public class NoSuchTokenException extends NoSuchElementException {

	public NoSuchTokenException(final String unknownToken) {
		super("Unknown token: \"" + unknownToken + "\"");
	}
}
