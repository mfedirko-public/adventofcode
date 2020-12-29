package com.adventofcode.day18;

import java.util.LinkedList;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Expression {
    private StringBuilder numBuilder = new StringBuilder();
    private LinkedList<Supplier<Long>> nums = new LinkedList<>();
    private LinkedList<Operators> ops = new LinkedList<>();
    private final IntStream chars;
    private int groupingDepth;
    private boolean usePrecedence;
    private StringBuilder nestedExpressionBuilder;

    Expression(String line) {
        this.chars = line.chars();
    }
    Expression(String line, boolean usePrecedence) {
        this(line);
        this.usePrecedence = usePrecedence;
    }

    long eval() {
        chars.forEach(c -> process((char)c));
        if (numBuilder.length() > 0) {
            long num = Long.parseLong(numBuilder.toString());
            nums.addLast(() -> num);
            numBuilder = new StringBuilder();
        }
        doEval();
        return nums.pop().get();
    }
    private void doEval() {
        while (nums.size() >= 2 && ops.size() >= 1) {
            long num1 = nums.pop().get();
            long num2 = nums.pop().get();
            Operators op = ops.pop();
            if (op == Operators.MULTIPLICATION && usePrecedence) {
                if (ops.peek() == Operators.MULTIPLICATION || ops.peek() == null) {
                    nums.push(() -> op.eval(num1, num2));
                } else {
                    long num3 = nums.pop().get();
                    Operators op2 = ops.pop();
                    nums.push(() -> op2.eval(num2, num3));
                    nums.push(() -> num1);
                    ops.push(op);
                }
            } else {
                nums.push(() -> op.eval(num1, num2));
            }
        }
    }

    private void process(char c) {
        final int origlGroupDepth = groupingDepth;
        if (c == '(') {
            if (groupingDepth == 0) {
                nestedExpressionBuilder = new StringBuilder();
            }
            groupingDepth++;
        } else if (c == ')') {
            groupingDepth--;
            if (groupingDepth == 0) {
                String expr = nestedExpressionBuilder.toString();
                nums.addLast(() -> new Expression(expr, usePrecedence).eval());
            }
        }

        if (origlGroupDepth > 0) {
            nestedExpressionBuilder.append(c);
        } else if (groupingDepth == 0) {
            if (c >= '0' && c <= '9') {
                numBuilder.append(c);
            } else if ((c == ' ') && numBuilder.length() > 0) {
                long num = Long.parseLong(numBuilder.toString());
                nums.addLast(() -> num);
                numBuilder = new StringBuilder();
            } else if (c == '+' || c == '*') {
                ops.addLast(Operators.fromSymbol(c));
            }
        }
    }
}
