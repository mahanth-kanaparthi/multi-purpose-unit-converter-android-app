package com.mk.utils;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluator {
    public static double evaluateExpression(String expression) {
        // Regular expression with escaped hyphen (option 1)
        // Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)|(\\+|-|\\*|/)|(\\()|(\\))");

        // Regular expression with alternation (option 2)
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)|([+\\-*/])|(\\()|(\\))");
        Matcher matcher = pattern.matcher(expression);

        // Stacks for operands and operators
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        while (matcher.find()) {
            String token = matcher.group();

            if (token.matches("\\d+(\\.\\d+)?")) {
                // Operand: push it onto the operand stack
                operandStack.push(Double.parseDouble(token));
            } else if (token.matches("[+\\-*/]")) {
                // Operator: handle precedence
                while (!operatorStack.isEmpty() && precedence(token.charAt(0)) <= precedence(operatorStack.peek())) {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    char operator = operatorStack.pop();
                    double result = applyOperator(operand1, operand2, operator);
                    operandStack.push(result);
                }
                operatorStack.push(token.charAt(0));
            } else if (token.equals("(")) {
                // Left parenthesis: push it onto the operator stack
                operatorStack.push('(');
            } else if (token.equals(")")) {
                // Right parenthesis: evaluate expressions within parentheses
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    double operand2 = operandStack.pop();
                    double operand1 = operandStack.pop();
                    char operator = operatorStack.pop();
                    double result = applyOperator(operand1, operand2, operator);
                    operandStack.push(result);
                }
                operatorStack.pop(); // Pop the left parenthesis
            }
        }

        // Evaluate remaining operators
        while (!operatorStack.isEmpty()) {
            double operand2 = operandStack.pop();
            double operand1 = operandStack.pop();
            char operator = operatorStack.pop();
            double result = applyOperator(operand1, operand2, operator);
            operandStack.push(result);
        }

        return operandStack.pop();
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '(':
            case ')':
                return 0;
            default:
                return -1;
        }
    }

    private static double applyOperator(double operand1, double operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                return operand1 / operand2;
            default:
                return Double.NaN;
        }
    }

}
