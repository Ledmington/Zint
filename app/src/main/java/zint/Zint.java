package zint;

import java.io.*;

public class Zint {

	private static InputStream fin = System.in;

	public static void main(final String[] args) {
		if(args.length == 0) {
			System.out.println("No input file provided. Using direct input from console...\n(Type Ctrl+Z and enter to quit)\n");
		}

		parseInput();
	}

	private static void parseInput() {
		BufferedReader input = new BufferedReader(new InputStreamReader(fin));
		String line = null;
		do {
			if(line != null) System.out.println("Read: \"" + line + "\"");
			try {
				line = input.readLine();
			} catch (IOException e) {
				// silently ignore
			}
		} while(line != null);
		System.out.println("End-of-File detected.\nQuitting...");
	}
}
