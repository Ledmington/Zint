grammar zombie;

@header {
    package gen;
}

@lexer::members {
    public int lexicalErrors = 0;
}

prog: progbody EOF;

progbody: entityDeclaration+;

entityDeclaration:
	(
		ID IS A ZOMBIE
		| ID IS AN ENSLAVED_UNDEAD
		| ID IS A GHOST
		| ID IS A RESTLESS_UNDEAD
		| ID IS A VAMPIRE
		| ID IS A FREE_WILLED_UNDEAD
		| ID IS A DEMON
		| ID IS A DJINN
	) (
		SUMMON instruction* ANIMATE
		| SUMMON instruction* BIND
		| SUMMON instruction* DISTURB
		| TASK instruction* ANIMATE
		| TASK instruction* BIND
	);

instruction: REMEMBER NUMBER | FORGET;

// Keywords
IS: 'is';
A: 'a';
AN: 'an';
REMEMBER: 'remember';
FORGET: 'forget';

// Entity types
ZOMBIE: 'zombie';
GHOST: 'ghost';
VAMPIRE: 'vampire';
DEMON: 'demon';
DJINN: 'djinn';
ENSLAVED_UNDEAD: 'enslaved undead';
RESTLESS_UNDEAD: 'restless undead';
FREE_WILLED_UNDEAD: 'free-willed undead';

// Entity declaration statements
SUMMON: 'summon';
ANIMATE: 'animate';
BIND: 'bind';
DISTURB: 'disturb';
TASK: 'task';

DIGIT: ( '0' ..'9');
NUMBER: DIGIT+;
ID: ('a' ..'z' | 'A' ..'Z') ('a' ..'z' | 'A' ..'Z' | DIGIT)*;
WHITESP: ( '\t' | ' ' | '\r' | '\n')+ -> channel(HIDDEN);
//COMMENT  : '/*' .*? '*/' -> channel(HIDDEN) ;
ERR:
	. {
    System.out.println("Invalid char: " + getText() + " at line " + getLine());
    lexicalErrors++;
    } -> channel(HIDDEN);