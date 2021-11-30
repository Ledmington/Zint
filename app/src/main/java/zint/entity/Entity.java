package zint.entity;

import java.util.*;

public abstract class Entity {

	private final String name;
	private Optional<Integer> rememberedValue;

	protected Entity(String name, Optional<Integer> value) {
		this.name = name;
		this.rememberedValue = value;
	}

	protected Entity(String name, Integer value) {
		this(name, Optional.of(value));
	}

	protected Entity(String name) {
		this(name, Optional.empty());
	}

	public String getName() {
		return name;
	}

	public boolean hasValue() {
		return rememberedValue.isPresent();
	}

	public Integer getValue() {
		return rememberedValue.get();
	}

	public abstract void summon();
	public abstract void animate();
	public abstract void disturb();
	public abstract void bind();

}
