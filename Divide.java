package expression;

public class Divide extends BinaryOperation {

    public Divide(final PriorityExpression x, final PriorityExpression y) {
        super(x, y);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected String getOperationString() {
        return "/";
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    public boolean uniqueCommutative() {
        return true;
    }

    @Override
    public double calculate(double x,double y) {
        return x / y;
    }

    @Override
    protected int calculate(int x, int y) {
        return x / y;
    }
}
