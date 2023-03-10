package expression.exceptions;

import expression.TripleExpression;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        ExpressionParser parser = new ExpressionParser(new StringCharSource(expression));
        TripleExpression ans = (TripleExpression) parser.parseExpression();
        System.out.println(ans.evaluate(1, 2, 0));
    }
}
