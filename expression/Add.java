package expression;

public class Add extends BinaryOperation {

    public Add(PriorityExpression x, PriorityExpression y) {
        super( x, y);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    protected String getOperationString() {
        return "+";
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    protected double calculate(double x, double y) {
        return x + y;
    }

    @Override
    protected int calculate(int x, int y) {
        return x + y;
    }
}
