package com.ledmington.zint;

import com.ledmington.zint.ast.EntityDeclaration;
import com.ledmington.zint.ast.EntityType;
import com.ledmington.zint.ast.Node;
import com.ledmington.zint.ast.Program;
import com.ledmington.zint.gen.ZombieParser;
import com.ledmington.zint.gen.ZombieParser.entityDeclaration;
import com.ledmington.zint.gen.ZombieParser.prog;
import com.ledmington.zint.gen.ZombieParser.progbody;

import java.util.List;

public final class Converter {

	public static Node convertToAST(final ZombieParser.Node raw) {
	if(raw instanceof final prog p) {
		return convertProg(p);
	}throw new IllegalArgumentException("Could not convert given node.");
	}

	private static Node convertProg(final prog p) {
	return new Program(convertProgBody(p.progbody()));
	}

	private static List<EntityDeclaration> convertProgBody(final progbody pb) {
	return pb.entityDeclaration().stream().map(Converter::convertEntityDeclaration).toList();
	}

	private static EntityDeclaration convertEntityDeclaration(final entityDeclaration ed) {
	return new EntityDeclaration(
			((ZombieParser.sequence_0)ed.or_0_0().match()).ID_0().literal(),
			switch(((ZombieParser.sequence_0)ed.or_0_0().match()).ZOMBIE_3().literal()){
				case "zombie" -> EntityType.ZOMBIE;
				default -> throw new IllegalArgumentException("Unknown entity type.");
			}
	);
	}
}
