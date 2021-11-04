package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.UtilFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MailServerTest {

    private static final InputStream systemIn = System.in;
    private static final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() throws UnsupportedEncodingException {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut, false, StandardCharsets.UTF_8.toString()));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(testIn);
    }

    private String getOutputFromConsole() {
        return testOut.toString(StandardCharsets.UTF_8);
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void test_write_message_to_console() {
        final String testString = "Subject: #{subject}\nMessage content: #{messageContent}";
        provideInput(testString);

        MailServer mailServer = new MailServer(new UtilFileReader());
        mailServer.send("", testString);

        assertEquals(testString, getOutputFromConsole());
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    public void test_write_message_to_file() {
        String subject = "test subject";
        String content = "test content";
        final String testString = "Subject: #{" + subject + "}\nContent: #{" + content + "}";
        provideInput(testString);
        List<String> testInput = Arrays.asList(subject, content);

        MockFileReader mockFileReader = new MockFileReader(testInput);

        MailServer mailServer = new MailServer(mockFileReader);
        mailServer.sendToFile("");

        assertAll(() -> mockFileReader.writeToFile(testString));
    }


    static class MockFileReader extends UtilFileReader {

        private final List<String> testInput;

        public MockFileReader(List<String> testInput) {
            this.testInput = testInput;
        }

        @Override
        public List<String> getInputFromFile() {
            return testInput;
        }

        @Override
        public void writeToFile(String input) {
        }
    }
}
