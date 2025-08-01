prog = progbody ;
progbody = entityDeclaration entityDeclaration* ;

entityDeclaration =
	(
		  ( id IS A ZOMBIE )
		| ( id IS AN ENSLAVED_UNDEAD )
		| ( id IS A GHOST )
		| ( id IS A RESTLESS_UNDEAD )
		| ( id IS A VAMPIRE )
		| ( id IS A FREE_WILLED_UNDEAD )
		| ( id IS A DEMON )
		| ( id IS A DJINN )
	) (
		  ( SUMMON instruction* ANIMATE )
		| ( SUMMON instruction* BIND )
		| ( SUMMON instruction* DISTURB )
		| ( TASK instruction* ANIMATE )
		| ( TASK instruction* BIND )
	);

instruction = ( REMEMBER number ) | FORGET ;
id = (
         "A" | "B" | "C" | "D" | "E" | "F" | "G"
       | "H" | "I" | "J" | "K" | "L" | "M" | "N"
       | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
       | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
       | "c" | "d" | "e" | "f" | "g" | "h" | "i"
       | "j" | "k" | "l" | "m" | "n" | "o" | "p"
       | "q" | "r" | "s" | "t" | "u" | "v" | "w"
       | "x" | "y" | "z"
       ) ( (
                "A" | "B" | "C" | "D" | "E" | "F" | "G"
              | "H" | "I" | "J" | "K" | "L" | "M" | "N"
              | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
              | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
              | "c" | "d" | "e" | "f" | "g" | "h" | "i"
              | "j" | "k" | "l" | "m" | "n" | "o" | "p"
              | "q" | "r" | "s" | "t" | "u" | "v" | "w"
              | "x" | "y" | "z"
       ) | DIGIT )* ;
number = DIGIT DIGIT* ;

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
ENSLAVED_UNDEAD = "enslaved undead";
RESTLESS_UNDEAD = "restless undead";
FREE_WILLED_UNDEAD = "free-willed undead";

SUMMON = "summon";
ANIMATE = "animate";
BIND = "bind";
DISTURB = "disturb";
TASK = "task";

DIGIT = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9";

_WHITESPACE = ( " " | "\t" | "\n" )+ ;
