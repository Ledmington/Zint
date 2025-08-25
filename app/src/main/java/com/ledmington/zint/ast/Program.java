package com.ledmington.zint.ast;

import java.util.List;

public record Program(List<EntityDeclaration> declarations) implements Node {
}
