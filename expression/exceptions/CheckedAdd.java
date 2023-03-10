package expression.exceptions;

import expression.Add;
import expression.PriorityExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(PriorityExpression x, PriorityExpression y) {
        super( x, y);
    }

    @Override
    protected int calculate(int x, int y) {
        if ((y > 0 && Integer.MAX_VALUE - y < x) || (y <= 0 && Integer.MIN_VALUE - y > x)) {
            throw new Overflow();
        }
        return x + y;
    }
}
