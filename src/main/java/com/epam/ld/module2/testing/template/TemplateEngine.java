package com.epam.ld.module2.testing.template;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.util.UtilFileReader;
import com.epam.ld.module2.testing.exception.IllegalValueException;

import java.util.List;
import java.util.Scanner;

/**
 * The type Template engine.
 */
public class TemplateEngine {
    /**
     * Generate message string.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessage(Template template, Client client) {

        String messageTemplate = template.getSubject() + "\n" + template.getMessageContent() + "\n" + template.getTag();

        try (Scanner scanner = new Scanner(System.in)) {
            String subject = this.getString(scanner, "Please, enter the subject: ");
            String messageWithSubject = messageTemplate.replace("subject", subject);
            String messageContent = this.getString(scanner, "Please, enter the messageContent: ");
            String messageWithContent = messageWithSubject.replace("messageContent", messageContent);
            String finalMessage = this.getString(scanner, "Please, enter the tag: ");
            return messageWithContent.replace("tag", finalMessage);
        }
    }

    /**
     * Generate message string from file.
     *
     * @param template the template
     * @param client   the client
     * @return the string
     */
    public String generateMessageFromFile(Template template, Client client) {
        UtilFileReader reader = new UtilFileReader();
        String messageTemplate = template.getSubject() + "\n" + template.getMessageContent() + "\n" + template.getTag();
        List<String> inputFromFile = reader.getInputFromFile();

        String subject = inputFromFile.get(0);
        String messageContent = inputFromFile.get(1);
        String tag = inputFromFile.get(2);
        String messageWithSubject = messageTemplate.replace("subject", subject);
        String messageWithContent = messageWithSubject.replace("messageContent", messageContent);
        return messageWithContent.replace("tag", tag);
    }

    private String getString(Scanner scanner, String prompt) throws IllegalValueException {
        System.out.print(prompt);
        if (scanner.hasNext()) {
            String string = scanner.nextLine();
            if (string == null || string.length() == 0) {
                throw new IllegalValueException("Message cannot be empty");
            }
            return string;
        } else {
            throw new IllegalValueException("You should enter message content");
        }
    }
}
