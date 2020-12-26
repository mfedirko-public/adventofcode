package com.adventofcode.day16;

import com.adventofcode.FileStreamSupport;

import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 {
    public static void main(String[] args) {
        InputStream file = Day16.class.getResourceAsStream("/day16.txt");
        Input input = new Input();
        FileStreamSupport.toStream(file, "\n\n")
                .forEach(input::nextSection);
        int sum = 0;
        List<Integer[]> validTickets = new ArrayList<>();
        for (Integer[] ticket : input.tickets) {
            boolean valid = true;
            for (int val : ticket) {
                if (input.rules.stream().noneMatch(r -> r.isValid(val))) {
                    sum += val;
                    valid = false;
                }
            }
            if (valid) {
                validTickets.add(ticket);
            }
        }
        System.out.printf("Pt1: %d\n", sum);
        determineFieldPositions(validTickets, input.rules);
        Map<String, Integer> labeledTicket = new HashMap<>();
        for (Rule rule : input.rules) {
            labeledTicket.put(rule.label, input.ownTicket[rule.position]);
        }

        long pt2 = labeledTicket.entrySet().stream()
                .filter(e -> e.getKey().startsWith("departure"))
                .mapToLong(Map.Entry::getValue)
                .reduce(1, (mult, next) -> mult * next);
        System.out.printf("Pt2: %d\n", pt2);
    }

    private static void determineFieldPositions(List<Integer[]> validTickets, Collection<Rule> rules) {
        Set<Integer> remainingPositions = new HashSet<>();
        for (int pos = 0; pos < validTickets.get(0).length; pos++) {
            remainingPositions.add(pos);
            final int ppos = pos;
            for (Rule rule : rules) {
                if (rule.position > -1) continue;
                if (validTickets.stream().allMatch(ticket -> rule.isValid(ticket[ppos]) )) {
                    rule.potentialPositions.add(pos);
                }
            }
        }
        while (remainingPositions.size() > 0) {
            for (Rule rule : rules) {
                Set<Integer> effectivePotentialPositions = new HashSet<>(rule.potentialPositions);
                effectivePotentialPositions.retainAll(remainingPositions);
                if (effectivePotentialPositions.size() == 1) {
                    rule.position = effectivePotentialPositions.iterator().next();
                    remainingPositions.remove(rule.position);
                }
            }
        }
    }


    public static class Input {
        public Set<Rule> rules = new HashSet<>();
        List<Integer[]> tickets = new ArrayList<>();
        int[] ownTicket;
        int ordinal = -1;
        private Parser parser;

        void nextSection(String lines) {
            parser = Parser.values()[++ordinal];
            parser.parse(lines, this);
        }
    }
    enum Parser {
        RULE {
            @Override
            void parse(String lines, Input input) {
                final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)");
                Arrays.stream(lines.split("\n"))
                        .forEach(l -> {
                            String[] parts = l.split(": ");
                            String name = parts[0];
                            Rule rule = new Rule(name);
                            Matcher matcher = PATTERN.matcher(l);
                            while (matcher.find()) {
                                rule.or(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
                            }
                            input.rules.add(rule);
                        });
            }
        },
        YOUR_TICKET {
            @Override
            void parse(String lines, Input input) {
                input.ownTicket = Arrays.stream(lines.split("\n")[1].split(","))
                                .mapToInt(Integer::parseInt).toArray();
            }
        },
        OTHER_TICKETS {
            @Override
            void parse(String lines, Input input) {
                lines = lines.substring(lines.indexOf('\n') + 1);
                Arrays.stream(lines.split("\n"))
                        .forEach(l -> {
                            Integer[] vals = Arrays.stream(l.split(","))
                                    .map(Integer::parseInt)
                                    .toArray(Integer[]::new);
                            input.tickets.add(vals);
                        });
            }
        };

        abstract void parse(String lines, Input input);
    }

    static class Rule {
        final String label;
        Predicate<Integer> validator;
        int position = -1;
        Set<Integer> potentialPositions = new HashSet<>();
        Rule(String label) {
            this.label = label;
            validator = i -> false;
        }

        void or(int min, int max) {
            validator = validator.or(i -> i >= min && i <= max);
        }

        boolean isValid(int val) {
            return validator.test(val);
        }
    }

}
