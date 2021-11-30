package zint.lexer.token;

public enum TokenType {
	// Basic tokens
	WHITESPACE,
	ID,
	NUMBER,
	STRING,
	ENTITY,

	// Entity declarations
	SUMMON,
	ANIMATE,
	BIND,
	DISTURB,
	TASK,
	IS_A
}
