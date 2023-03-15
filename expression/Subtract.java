package expression;

public class Subtract extends BinaryOperation {

    public Subtract(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    protected String getOperationString() {
        return "-";
    }

    @Override
    protected boolean isCommutative() {
        return false;
    }

    @Override
    protected int calculate(int x, int y) {
        return x - y;
    }

    @Override
    public double calculate(double x,double y) {
        return x - y;
    }
}
