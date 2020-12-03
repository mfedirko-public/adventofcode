package com.adventofcode.day2;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RuleReader {
    public static RuleWithPassword END_OF_FILE = new RuleWithPassword((char)0, 1, 1, "");
    private static final Pattern LINE_PATTERN = Pattern.compile("^(?<min>\\d+)-(?<max>\\d+)\\s+(?<ltr>[a-z]):\\s+(?<pwd>[a-z]+).*");
    private final BufferedReader reader;
    public RuleReader(InputStream inputStream) throws IOException {
        reader = new BufferedReader(
                    new InputStreamReader(
                            inputStream));
    }

    /**
     *
     * @return next valid RuleWithPassword if exists, RuleReader.END_OF_FILE otherwise.
     */
    public RuleWithPassword readNext() {
        try {
            String line = reader.readLine();
            if (line == null) {
                return END_OF_FILE;
            }
            char letter;
            int minOccurs, maxOccurs;
            String password;
            Matcher matcher = LINE_PATTERN.matcher(line);
            if (matcher.matches()) {
                letter = matcher.group("ltr").toCharArray()[0];
                minOccurs = Integer.parseInt(matcher.group("min"));
                maxOccurs = Integer.parseInt(matcher.group("max"));
                password = matcher.group("pwd");
                return new RuleWithPassword(letter, minOccurs, maxOccurs, password);
            } else {
                return readNext();
            }
        } catch (IOException ex) {
            return END_OF_FILE;
        }
    }

}
