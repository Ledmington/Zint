prog = entity_declaration+ ;

entity_declaration = entity_name_and_type entity_statement+;
entity_name_and_type = ID IS ( A ZOMBIE
                          | AN ENSLAVED_UNDEAD
                          | A GHOST
                          | A RESTLESS_UNDEAD
                          | A VAMPIRE
                          | A FREE_WILLED_UNDEAD
                          | A DEMON
                          | A DJINN ) ;

entity_statement = STUMBLE
                 | REND
                 | TURN
                 | ANIMATE ID?
                 | BANISH ID?
                 | DISTURB ID?
                 | FORGET ID?
                 | INVOKE ID?
                 | MOAN ID?
                 | REMEMBER NUMBER
                 | SAY ID? STRING_LITERAL
                 | REMEMBERING ID? ID
                 | SHAMBLE entity_statement+ UNTIL ID
                 | SHAMBLE entity_statement+ AROUND
                 | TASTE ID GOOD entity_statement+ BAD entity_statement+ SPIT
                 | SUMMON entity_statement+ ANIMATE ID?
                 | SUMMON entity_statement+ BIND
                 | SUMMON entity_statement+ DISTURB ID?
                 | TASK ID entity_statement+ ANIMATE ID?
                 | TASK ID entity_statement+ BIND ;

IS = "is";
A = "a";
AN = "an";
REMEMBER = "remember";
REMEMBERING = "remembering";
FORGET = "forget";
SAY = "say";
BANISH = "banish";
INVOKE = "invoke";
MOAN = "moan";
SHAMBLE = "shamble";
UNTIL = "until";
AROUND = "around";
STUMBLE = "stumble";
TASTE = "taste";
GOOD = "good";
BAD = "bad";
SPIT = "spit";
REND = "rend";
TURN = "turn";

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

DOUBLE_QUOTE = "\"" ;
EXCLAMATION_MARK = "!" ;

NUMBER = DIGIT+ ;

DIGIT = "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9";
LETTER = "A" | "B" | "C" | "D" | "E" | "F" | "G"
       | "H" | "I" | "J" | "K" | "L" | "M" | "N"
       | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
       | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
       | "c" | "d" | "e" | "f" | "g" | "h" | "i"
       | "j" | "k" | "l" | "m" | "n" | "o" | "p"
       | "q" | "r" | "s" | "t" | "u" | "v" | "w"
       | "x" | "y" | "z" ;

ID = LETTER ( LETTER | DIGIT )* ;
STRING_LITERAL = DOUBLE_QUOTE ( LETTER | " " | EXCLAMATION_MARK )* DOUBLE_QUOTE ;

_WHITESPACE = ( " " | "\t" | "\n" )+ ;
