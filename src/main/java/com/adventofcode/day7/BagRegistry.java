package com.adventofcode.day7;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BagRegistry {
    private Map<BagKey, Set<Bag>> index = new HashMap<>();

    private final Pattern SUB_BAG_PATTERN = Pattern.compile("(\\d+) ([a-z\\s]+) bag");

    public void register(String rule) {
        int bagNameEndIndex = rule.indexOf(" bags contain ");
        String parentColor = rule.substring(0, bagNameEndIndex);
        Matcher matcher = SUB_BAG_PATTERN.matcher(rule);
        Bag bag = new Bag(parentColor);
        BagKey parentKey = new BagKey(parentColor, BagKey.Type.CHILD);
        while (matcher.find()) {
            int childCount = Integer.parseInt(matcher.group(1));
            String childColor = matcher.group(2);
            Bag child = new Bag(childColor, childCount);
            BagKey key = new BagKey(childColor, BagKey.Type.PARENT);
            registerBag(key, bag);
            registerBag(parentKey, child);
        }
    }

    private void registerBag(BagKey key, Bag bag) {
        Set<Bag> existing =
                index.getOrDefault(key, new HashSet<>());
        existing.add(bag);
        index.put(key, existing);
    }

    public Set<String> getCumulativeParentSet(String color) {
        Set<String> parents = new HashSet<>();
        BagKey key = new BagKey(color, BagKey.Type.PARENT);
        Set<Bag> currentParents = index.getOrDefault(key, Collections.emptySet());
        for (Bag bag : currentParents) {
            parents.add(bag.color);
            parents.addAll(getCumulativeParentSet(bag.color));
        }
        return parents;
    }

    public long getCumulativeChildCount(String color) {
        long count = 0;
        BagKey key = new BagKey(color, BagKey.Type.CHILD);
        Set<Bag> children = index.getOrDefault(key, Collections.emptySet());
        for (Bag bag : children) {
            count += bag.count;
            count += bag.count * getCumulativeChildCount(bag.color);
        }
        return count;
    }


}
