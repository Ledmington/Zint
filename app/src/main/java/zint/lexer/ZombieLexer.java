package zint.lexer;

import zint.lexer.token.*;

import java.io.*;
import java.util.*;

public class ZombieLexer implements Iterator<Token> {

	private final Reader input;
	private boolean inputTerminated = false;
	private String line = null;

	public ZombieLexer(final InputStream input) {
		this.input = new BufferedReader(new InputStreamReader(input));
	}

	private void readChar() {
		int charRead;
		try {
			charRead = input.read();
			if(charRead == -1) inputTerminated = true;
			else line += (char)charRead;
		} catch (IOException e) {
			inputTerminated = true;
		}
	}

	public boolean hasNext() {
		if(inputTerminated) return false;

		if(line == null) {
			line = "";
			readChar();
		}

		return line != null && line.length()>0;
	}

	public Token next() {
		if(inputTerminated) {
			throw new IllegalStateException("Cannot call next() if input is terminated");
		}

		if(line == null) line = "";
		readChar();

		while(hasNext() && matchesToken(line).isPresent()) {
			readChar();
		}

		String tmp;
		if(line.length() > 1 && hasNext()) {
			char lastChar = line.charAt(line.length() - 1);
			tmp = line.substring(0, line.length() - 1);
			line = String.valueOf(lastChar);
		}
		else {
			tmp = line;
			line = null;
		}

		Optional<TokenType> foundToken = matchesToken(tmp);
		if(foundToken.isEmpty()) {
			throw new NoSuchTokenException(tmp);
		}
		return new Token(foundToken.get(), tmp);
	}

	private Optional<TokenType> matchesToken(final String word) {
		for (TokenType t : TokenType.values()) {
			if (Tokens.tokenMap.get(t).matcher(word).matches()) {
				return Optional.of(t);
			}
		}
		return Optional.empty();
	}
}
