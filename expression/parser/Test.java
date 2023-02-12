package expression.parser;

import expression.Add;
import expression.Const;
import expression.PriorityExpression;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse("3 - "));
    }

    public static PriorityExpression a() {
        return new Add(new Const(3), new Const(2));
    }
}
