package expression.exceptions;

import expression.Multiply;
import expression.PriorityExpression;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    protected int calculate(int x, int y) {
        if ((y > 0 && (Integer.MIN_VALUE / y > x || Integer.MAX_VALUE / y < x)) ||
                (x > 0 && (Integer.MIN_VALUE / x > y || Integer.MAX_VALUE / x < y)) ||
                (y < 0 && (Integer.MAX_VALUE / y > x)) ||
                (x < 0 && (Integer.MAX_VALUE / x > y))) {
            throw new Overflow();
        }
        return x * y;
    }
}
