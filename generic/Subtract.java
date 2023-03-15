package expression.generic;

public class Subtract<T extends Number> extends BinaryOperation<T> {

    public Subtract(GenericExpression<T> x, GenericExpression<T> y) {
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
    protected GenericNumber<T> calculate(GenericNumber<T> x, GenericNumber<T> y) {
        return x.subtract(y.getValue());
    }
}
