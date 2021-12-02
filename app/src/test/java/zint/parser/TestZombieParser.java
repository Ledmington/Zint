package zint.parser;

import org.junit.jupiter.api.*;
import zint.lexer.*;
import zint.parser.instruction.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestZombieParser {

	private Parser p;

	// This method has been extracted just for readability
	private void buildParser(final String code) {
		p = new ZombieParser(new ZombieLexer(new ByteArrayInputStream(code.getBytes())));
	}

	@Test
	public void checkAllInstructionsArePresent() {
		for(InstructionType inst : InstructionType.values()) {
			assertTrue(Instructions.instructionMap.containsKey(inst), "InstructionsMap does not contain \"" + inst.name() + "\"");
		}
	}

	@Test
	public void empty() {
		buildParser("");
		assertThrows(IllegalStateException.class, () -> p.next());
		for(int i=0; i<10; i++) {
			assertFalse(p.hasNext());
			assertThrows(IllegalStateException.class, () -> p.next());
		}
	}

	@Test
	public void entityDeclaration() {
		buildParser("mario is a zombie");
		assertTrue(p.hasNext());
		assertEquals(InstructionType.ENTITY_DECLARATION, p.next().getType());
		assertFalse(p.hasNext());
	}
}
