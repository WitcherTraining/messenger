package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.exception.IllegalValueException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class TemplateEngineTest {

    private TemplateEngine templateEngine;
    private Template template;
    private static final InputStream systemIn = System.in;
    private static final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() throws UnsupportedEncodingException {
        templateEngine = new TemplateEngine();
        template = new Template();
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut, false, StandardCharsets.UTF_8.toString()));
    }

    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        System.setIn(testIn);
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void test_throw_illegal_value_exception() {
        provideInput("");
        Executable executable = () -> templateEngine.generateMessage(new Template(), new Client());
        assertThrows(IllegalValueException.class, executable, "You should enter message content");
    }

    @Test
    public void test_message_should_be_with_all_fields() throws IllegalValueException {
        provideInput("My subject\nMy content\nMy tag");
        String message = templateEngine.generateMessage(template, new Client());
        assertEquals("Subject: My subject\nMy content: My content\nMy link: My tag", message);
    }

    @TestFactory
    public Collection<DynamicTest> test_template_returns_correct_parameters() {
        return Arrays.asList(
                dynamicTest("Should return correct subject template",
                        () -> assertEquals("Subject: subject", template.getSubject())),
                dynamicTest("Should return correct content template",
                        () -> assertEquals("My content: messageContent", template.getMessageContent())),
                dynamicTest("Should return correct tag template",
                        () -> assertEquals("My link: tag", template.getTag()))
        );
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "content",
            "My content",
            "2423223"
    })
    public void test_generate_message_content(String testContent) throws IllegalValueException {
        provideInput("subject\n" + testContent + "\ntag");

        TemplateEngine templateEngine = new TemplateEngine();
        String message = templateEngine.generateMessage(new Template(), new Client());

        assertEquals("Subject: subject\nMy content: " + testContent + "\nMy link: tag", message);
    }
}
