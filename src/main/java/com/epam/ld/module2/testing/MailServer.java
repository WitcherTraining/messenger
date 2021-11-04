package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.util.UtilFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Mail server class.
 */
public class MailServer {

    private final UtilFileReader utilFileReader;

    public MailServer(UtilFileReader utilFileReader) {
        this.utilFileReader = utilFileReader;
    }

    /**
     * Send notification.
     *
     * @param addresses      the addresses
     * @param messageContent the message content
     */
    public void send(String addresses, String messageContent) {
        System.out.print(messageContent);
    }

    /**
     * Send notification to file.
     *
     * @param messageContentFromFile the message content
     */
    public void sendToFile(String messageContentFromFile) {
        this.utilFileReader.writeToFile(messageContentFromFile);
    }
}
