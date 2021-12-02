package zint.parser;

import zint.lexer.*;
import zint.lexer.token.*;
import zint.parser.instruction.*;

import java.util.*;

public class ZombieParser implements Parser {

	private final ZombieLexer lexer;
	private final List<Token> tokens = new LinkedList<>();

	public ZombieParser(final ZombieLexer lexer) {
		this.lexer = lexer;
	}

	public boolean hasNext() {
		return lexer.hasNext();
	}

	public Instruction next() {
		if(!hasNext() && tokens.isEmpty()) {
			throw new IllegalStateException("Cannot call next() if input is terminated");
		}

		Optional<InstructionType> newInst = Optional.empty();
		while(hasNext()) {
			tokens.add(lexer.next());
			newInst = matchesInstruction();
			if(newInst.isPresent()) break;
		}

		if(newInst.isEmpty() && !tokens.isEmpty()) {
			throw new UnknownInstructionException(tokens);
		}

		tokens.clear();

		return new Instruction(newInst.get());
	}

	private Optional<InstructionType> matchesInstruction() {
		for(InstructionType instType : InstructionType.values()) {
			if(Instructions.instructionMap.get(instType).test(tokens)) {
				return Optional.of(instType);
			}
		}
		return Optional.empty();
	}
}
