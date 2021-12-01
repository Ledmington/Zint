package zint;

import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestConsole {

	private BufferedWriter out;

	@BeforeEach
	public void setup() throws IOException {
		File file = File.createTempFile("tmp", null);
		file.deleteOnExit();
		out = new BufferedWriter(new FileWriter(file));
		System.setIn(new FileInputStream(file));
	}

	@Test
	public void test() {
		try {
			out.write("hello\nwelcome\n");
			assertEquals(Zint.parseInput(), 0);
			assertEquals(Zint.fin, System.in);
		} catch (IOException e) {
			fail();
		}
	}
}
