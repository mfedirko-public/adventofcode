package com.adventofcode.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassportReader {
    private static final Pattern LINE_PATTERN = Pattern.compile("\\s*([a-z]{3}):([a-z0-9#]+)\\s*");
    private BufferedReader reader;
    public PassportReader(InputStream inputStream) throws IOException {
        reader = new BufferedReader(
                new InputStreamReader(
                        inputStream));
    }

    public Passport next() {
        try {
            Map<String, String> map = new HashMap<>();
            String line;
            do {
                line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    Matcher matcher = LINE_PATTERN.matcher(line);
                    int grpCnt = matcher.toMatchResult().groupCount();
                    while (matcher.find()) {
                        String key = matcher.group(1);
                        String value = matcher.group(2);
                        map.put(key, value);
                    }
                }
            } while (line != null && !line.isEmpty());
            if (line == null && map.isEmpty()) return null;
            else return new Passport(map);
        } catch (IOException ex) {
            throw new IllegalStateException("Unreadable file!", ex);
        }

    }
}
