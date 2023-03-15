package expression.exceptions;

import expression.PriorityExpression;
import expression.UnaryOperation;

public class Log10 extends UnaryOperation {
    public Log10(PriorityExpression x) {
        super(x);
    }

    @Override
    protected int calculate(int x) {
        int ans = 0;
        if (x <= 0) {
            throw new IllegalArgumentException(" illegal arg for log");
        }
        while (x >= 10) {
            x /= 10;
            ans += 1;
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
        return "log10";
    }
}
