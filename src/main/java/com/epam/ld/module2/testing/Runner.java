package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.exception.IllegalValueException;
import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import com.epam.ld.module2.testing.util.ResourceUtil;
import com.epam.ld.module2.testing.util.UtilFileReader;

import java.io.IOException;

public class Runner {
    /**
     * Method main
     *
     * @param args set file paths if needed
     * @throws IllegalValueException if execution failed
     */
    public static void main(String[] args) throws IllegalValueException {
        ResourceUtil.setPropertiesIfExists(args);
        Messenger messenger = new Messenger(new MailServer(new UtilFileReader()), new TemplateEngine());

        if (args.length == 0) {
            messenger.sendMessage(new Client(), new Template());
        } else {
            messenger.sendMessageToFile(new Client(), new Template());
        }
    }
}
