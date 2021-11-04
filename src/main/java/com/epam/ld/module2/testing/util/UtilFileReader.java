package com.epam.ld.module2.testing.util;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UtilFileReader {
    /**
     * Write data to file
     *
     * @param userInput data to file
     */
    public void writeToFile(String userInput) {
        File file = new File(ResourceUtil.getOutputMessage());

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath())) {
            bufferedWriter.write(userInput);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Get data from file.
     *
     * @return returns data from file
     */
    public List<String> getInputFromFile() {
        List<String> result = new ArrayList<>();
        File file = new File(ResourceUtil.getUserInput());

        try (InputStream inputStream = new FileInputStream(file)){
            result = readFromInputStream(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private List<String> readFromInputStream(InputStream inputStream) throws IOException {
        List<String> result;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            result = br.lines().collect(Collectors.toList());
        }
        return result;
    }
}
