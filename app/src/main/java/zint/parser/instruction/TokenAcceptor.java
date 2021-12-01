package zint.parser.instruction;

import zint.lexer.token.*;

import java.util.*;
import java.util.function.*;

public interface TokenAcceptor extends Predicate<List<Token>> {
}
