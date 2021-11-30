package zint.entity;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestEntity {

	private Entity e;

	@Test
	public void noValue() {
		String name = "zombie";
		e = new DummyEntity(name);
		assertEquals(e.getName(), name);
		assertFalse(e.hasValue());
	}

	@Test
	public void withValue() {
		int n = 123;
		e = new DummyEntity("a", n);
		assertTrue(e.hasValue());
		assertEquals(e.getValue(), n);
	}
}
