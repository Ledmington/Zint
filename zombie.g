prog = progbody ;
progbody = entityDeclaration, { entityDeclaration } ;

entityDeclaration =
	(
		( ID, IS, A, ZOMBIE )
		| ( ID, IS, AN, ENSLAVED UNDEAD )
		| ( ID, IS, A, GHOST )
		| ( ID, IS, A, RESTLESS UNDEAD )
		| ( ID, IS, A, VAMPIRE )
		| ( ID, IS, A, FREE WILLED UNDEAD )
		| ( ID, IS, A, DEMON )
		| ( ID, IS, A, DJINN )
	), (
		( SUMMON, { instruction }, ANIMATE )
		| ( SUMMON, { instruction }, BIND )
		| ( SUMMON, { instruction }, DISTURB )
		| ( TASK, { instruction }, ANIMATE )
		| ( TASK, { instruction }, BIND )
	);

instruction = ( REMEMBER, NUMBER ) | FORGET ;

IS = "is";
A = "a";
AN = "an";
REMEMBER = "remember";
FORGET = "forget";

ZOMBIE = "zombie";
GHOST = "ghost";
VAMPIRE = "vampire";
DEMON = "demon";
DJINN = "djinn";
ENSLAVED UNDEAD = "enslaved undead";
RESTLESS UNDEAD = "restless undead";
FREE WILLED UNDEAD = "free-willed undead";

SUMMON = "summon";
ANIMATE = "animate";
BIND = "bind";
DISTURB = "disturb";
TASK = "task";

DIGIT = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9";
NUMBER = DIGIT, { DIGIT } ;
letter = "A" | "B" | "C" | "D" | "E" | "F" | "G"
       | "H" | "I" | "J" | "K" | "L" | "M" | "N"
       | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
       | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
       | "c" | "d" | "e" | "f" | "g" | "h" | "i"
       | "j" | "k" | "l" | "m" | "n" | "o" | "p"
       | "q" | "r" | "s" | "t" | "u" | "v" | "w"
       | "x" | "y" | "z" ;
ID = letter, { letter | DIGIT };
