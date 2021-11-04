package com.epam.ld.module2.testing.util;

public class ResourceUtil {
    private static String userInput = ClassLoader.getSystemResource("userInput.txt").getFile();
    private static String outputMessage = ClassLoader.getSystemResource("outputMessage.txt").getFile();

    public static String getUserInput() {
        return userInput;
    }

    public static String getOutputMessage() {
        return outputMessage;
    }

    /**
     * Set properties such as file paths
     *
     * @param args array of file paths, should be for userInput.txt and outputMessage.txt files
     */
    public static void setPropertiesIfExists(String[] args) {
        if (args != null && args.length == 2) {
            userInput = args[0];
            outputMessage = args[1];
        }
    }
}
