package expression.exceptions;

import expression.PriorityExpression;
import expression.Set;

public class CheckedSet extends Set {
    public CheckedSet(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    protected int calculate(int x, int y) {
        return x | (1 << y);
    }
}
