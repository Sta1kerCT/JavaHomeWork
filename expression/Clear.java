package expression;

public class Clear extends BinaryOperation {
    public Clear(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected String getOperationString() {
        return "clear";
    }

    @Override
    protected double calculate(double x, double y) {
        return 0;
    }

    @Override
    protected int calculate(int x, int y) {
        return x & (~(1 << y));
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean uniqueCommutative() {
        return true;
    }
}
