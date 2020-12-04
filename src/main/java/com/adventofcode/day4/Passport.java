package com.adventofcode.day4;

import java.util.*;
import java.util.regex.Pattern;

public class Passport {
    private static final List<String> requiredFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
    private static final Pattern byr = Pattern.compile("^(19[2-9][0-9]|200[0-2])$");
    private static final Pattern iyr = Pattern.compile("^(201[0-9]|2020)$");
    private static final Pattern eyr = Pattern.compile("^(202[0-9]|2030)$");
    private static final Pattern hgt = Pattern.compile("^(1[5-8][0-9]|19[0-3])cm|((59|6[0-9]|7[0-6])in)$");
    private static final Pattern hcl = Pattern.compile("^#[0-9a-f]{6}$");
    private static final Pattern ecl = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$");
    private static final Pattern pid = Pattern.compile("^[0-9]{9}$");
    private static Map<String, Pattern> validators = new HashMap<>();
    static {
        validators.put("byr", byr);
        validators.put("iyr", iyr);
        validators.put("eyr", eyr);
        validators.put("hgt", hgt);
        validators.put("hcl", hcl);
        validators.put("ecl", ecl);
        validators.put("pid", pid);
    }
    private final Map<String, String> fields;

    public Passport(Map<String, String> fields) {
        this.fields = Objects.requireNonNull(fields, "Passport fields cannot be null!");
    }

    public boolean isValid() {
        return fields.keySet().containsAll(requiredFields);
    }
    public boolean isValidStrict() {
        return isValid() && validateStrict();
    }

    private boolean validateStrict() {
        for (Map.Entry<String, Pattern> validator : validators.entrySet()) {
            String value = fields.get(validator.getKey());
            if (!validator.getValue().matcher(value).matches()) {
                return false;
            }
        }
        return true;
    }
}
