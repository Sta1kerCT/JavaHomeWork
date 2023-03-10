package expression.exceptions;

import expression.PriorityExpression;
import expression.UnaryOperation;

public class Pow10 extends UnaryOperation {

    public Pow10(PriorityExpression x) {
        super(x);
    }

    @Override
    protected int calculate(int x) {
        int ans = 1;
        if (x < 0 || x >= 10) {
            throw new IllegalArgumentException(" illegal arg");
        }
        for (int i = 0; i < x; i++) {
            ans *= 10;
        }
        return ans;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    protected String operationString() {
        return "pow10";
    }
}
