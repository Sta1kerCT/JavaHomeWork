package expression.exceptions;

import expression.PriorityExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    public int calculate(int x, int y) {
        if ((y <= 0 && Integer.MAX_VALUE + y < x) || (y > 0 && Integer.MIN_VALUE + y > x)) {
            throw new Overflow();
        }
        return x - y;
    }
}
