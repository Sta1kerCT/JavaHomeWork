package expression;

public class Multiply extends BinaryOperation {
    public Multiply(PriorityExpression x, PriorityExpression y) {
        super(x, y);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected String getOperationString() {
        return "*";
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    protected boolean isCommutative() {
        return true;
    }


    @Override
    protected int calculate(int x, int y) {
        return x * y;
    }

    @Override
    public double calculate(double x,double y) {
        return x * y;
    }
}
