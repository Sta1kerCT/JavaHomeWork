package expression.generic;

public class Divide<T extends Number> extends BinaryOperation<T> {

    public Divide(final GenericExpression<T> x, final GenericExpression<T> y) {
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
    protected GenericNumber<T> calculate(GenericNumber<T> x, GenericNumber<T> y) {
        return x.divide(y.getValue());
    }
}
