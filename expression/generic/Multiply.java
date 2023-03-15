package expression.generic;

public class Multiply<T extends Number> extends BinaryOperation<T> {
    public Multiply(GenericExpression<T> x, GenericExpression<T> y) {
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
    protected GenericNumber<T> calculate(GenericNumber<T> x, GenericNumber<T> y) {
        return x.multiply(y.getValue());
    }
}
