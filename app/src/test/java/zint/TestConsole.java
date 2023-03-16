/*
 * Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
 *
 * This file is part of Zint.
 *
 * Zint can not be copied and/or distributed without
 * the express permission of Filippo Barbari.
 */
package zint;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

import org.junit.jupiter.api.*;

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
