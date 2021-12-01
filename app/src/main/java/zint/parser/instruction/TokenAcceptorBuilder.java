package zint.parser.instruction;

import zint.lexer.token.*;

import java.util.*;
import java.util.function.*;

public class TokenAcceptorBuilder {

	private final List<Predicate<Iterator<Token>>> pipeline = new LinkedList<>();

	public TokenAcceptorBuilder one(final TokenType type) {
		pipeline.add(it -> it.hasNext() && it.next().getType() == type);
		return this;
	}

	public TokenAcceptorBuilder any(final TokenType... types) {
		if(types.length < 2) {
			throw new IllegalArgumentException();
		}
		pipeline.add(it -> {
			if(!it.hasNext()) return false;
			TokenType t = it.next().getType();
			for(TokenType type : types) {
				if(t == type) return true;
			}
			return false;
		});
		return this;
	}

	public TokenAcceptor build() {
		if(pipeline.isEmpty()) {
			throw new IllegalStateException("Cannot build TokenAcceptor if the pipeline is empty.");
		}

		return tokens -> {
			Iterator<Token> it = tokens.iterator();
			for(var p : pipeline) {
				if(!p.test(it)) return false;
			}
			return !it.hasNext();
		};
	}
}