prog = entityDeclaration+ ;

entityDeclaration =	entityNameAndType entityBody;
entityNameAndType = id IS ( A ZOMBIE
                          | AN ENSLAVED_UNDEAD
                          | A GHOST
                          | A RESTLESS_UNDEAD
                          | A VAMPIRE
                          | A FREE_WILLED_UNDEAD
                          | A DEMON
                          | A DJINN ) ;
entityBody = SUMMON instruction+ ANIMATE
           | SUMMON instruction+ BIND
           | SUMMON instruction+ DISTURB
           | TASK id instruction+ ANIMATE
           | TASK id instruction+ BIND ;

instruction = FORGET
            | REMEMBER number
            | SAY string_literal
            | TASK id instruction+ ANIMATE ;
number = DIGIT+ ;
id = LETTER+ ;
string_literal = DOUBLE_QUOTE ( LETTER | WHITESPACE | EXCLAMATION_MARK )* DOUBLE_QUOTE ;

IS = "is";
A = "a";
AN = "an";
REMEMBER = "remember";
FORGET = "forget";
SAY = "say";

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
LETTER = "A" | "B" | "C" | "D" | "E" | "F" | "G"
       | "H" | "I" | "J" | "K" | "L" | "M" | "N"
       | "O" | "P" | "Q" | "R" | "S" | "T" | "U"
       | "V" | "W" | "X" | "Y" | "Z" | "a" | "b"
       | "c" | "d" | "e" | "f" | "g" | "h" | "i"
       | "j" | "k" | "l" | "m" | "n" | "o" | "p"
       | "q" | "r" | "s" | "t" | "u" | "v" | "w"
       | "x" | "y" | "z" ;
DOUBLE_QUOTE = "\"" ;
EXCLAMATION_MARK = "!" ;

WHITESPACE = ( " " | "\t" | "\n" )+ ;
