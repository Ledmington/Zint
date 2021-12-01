package zint.parser.instruction;

public abstract class Instruction {

	private final InstructionType type;

	protected Instruction(InstructionType type) {
		this.type = type;
	}

	public InstructionType getType() {
		return type;
	}
}
