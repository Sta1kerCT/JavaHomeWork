package expression;

public class UnaryMinus extends UnaryOperation {
    public UnaryMinus(PriorityExpression x) {
        super(x);
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
        return "-";
    }

    @Override
    protected int calculate(int x) {
        return -x;
    }
}
