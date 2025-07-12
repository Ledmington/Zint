# Zint
## the Java ZOMBIE Interpreter
Zint is an interpreter for the ZOMBIE esoteric programming language written in Java.
If you don't know the ZOMBIE language, go check out the [official documentation](https://www.dangermouse.net/esoteric/zombie.html) or the Esolang WIKI [page](https://esolangs.org/wiki/ZOMBIE).

## How to build
Download the parser generator with:
```bash
wget https://github.com/Ledmington/parser_generator/releases/download/snapshot-2025-07-12-54e63f9/parser-gen-cli-0.1.0.jar
```

Then run it to generate the parser:
```bash
java -jar parser-gen-cli-0.1.0.jar -v -g zombie.g -o ZombieParser.java -p gen
```
