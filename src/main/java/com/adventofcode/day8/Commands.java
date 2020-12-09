package com.adventofcode.day8;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Commands  {
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    static {
        COMMANDS.put("nop", (in, cf, ac) -> {});
        COMMANDS.put("acc", (in, cf, ac) -> ac.addAndGet(in));
        COMMANDS.put("jmp", (in, cf, ac) -> cf.moveFeed(in - 1));
    }

    public void execute(CommandFeed feed, AtomicInteger accumulator) {
        String[] cmdWithArg = feed.consume().split("\\s");
        String cmd = cmdWithArg[0];
        int arg = Integer.parseInt(cmdWithArg[1]);
        COMMANDS.get(cmd).execute(arg, feed, accumulator);
    }

}
