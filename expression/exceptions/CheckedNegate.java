package expression.exceptions;

import expression.PriorityExpression;
import expression.UnaryMinus;

public class CheckedNegate extends UnaryMinus {
    public CheckedNegate(PriorityExpression x) {
        super(x);
    }

    @Override
    protected int calculate(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new Overflow();
        }
        return -x;
    }
}
