package com.examples;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindPrimesTest {

    private final PrintStream stdOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(stdOut);
    }

    @Test
    void testNoFile() {
        Throwable ex = assertThrows(IllegalArgumentException.class,
                () -> FindPrimes.getFile(new String[]{}));

        assertEquals("Missing file path.", ex.getMessage());
    }

    @Test
    void testTooManyArguments() {
        Throwable ex = assertThrows(IllegalArgumentException.class,
                () -> FindPrimes.getFile(new String[]{"one", "two"}));

        assertEquals("Too many arguments.", ex.getMessage());
    }

    @Test
    void testInvalidFile() {
        File file = new File("src/test/resources/invalid.txt");

        assertThrows(NotOfficeXmlFileException.class,
                () -> FindPrimes.getPrimesFromFile(file));
    }

    @Test
    void testNoNumbers() throws IOException {
        File file = new File("src/test/resources/no_data.xlsx");

        FindPrimes.getPrimesFromFile(file);

        assertEquals("", outputStream.toString().trim());
    }

    @Test
    void testNegativeNumbers() throws IOException {
        File file = new File("src/test/resources/negative_numbers.xlsx");

        FindPrimes.getPrimesFromFile(file);

        assertEquals("1\n3", outputStream.toString().trim());
    }

    @Test
    void testFindAllPrimes() throws IOException {
        File file = new File("src/main/resources/vzorek_dat.xlsx");

        FindPrimes.getPrimesFromFile(file);

        String expected = "5645657\n15619\n1234187\n211\n7\n9788677\n23311\n54881";
        assertEquals(expected, outputStream.toString().trim());
    }
}