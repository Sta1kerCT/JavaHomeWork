package expression.exceptions;

import expression.Clear;
import expression.PriorityExpression;

public class CheckedClear extends Clear {
    public CheckedClear(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    protected int calculate(int x, int y) {
        return x & (~(1 << y));
    }
}
