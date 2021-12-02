package zint.lexer.token;

public enum TokenType {
	// Basic tokens
	WHITESPACE,
	NUMBER,
	STRING,
	ENTITY,

	// Entity declarations
	SUMMON,
	ANIMATE,
	BIND,
	DISTURB,
	TASK,
	IS,
	ARTICLE,

	// Task statements
	REMEMBER,
	MOAN,
	BANISH,
	FORGET,
	INVOKE,
	SAY,

	// Flow control
	SHAMBLE,
	UNTIL,
	AROUND,
	STUMBLE,
	TASTE,
	GOOD,
	BAD,
	SPIT,

	// Operators
	REMEMBERING,
	REND,
	TURN,

	ID
}
