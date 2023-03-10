package expression.exceptions;

import expression.Divide;
import expression.PriorityExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    protected int calculate(int x, int y) {
        if (y == 0) {
            throw new DivisionByZero();
        } else if (x == Integer.MIN_VALUE && y == -1) {
            throw new Overflow();
        } else {
            return x / y;
        }
    }
}
