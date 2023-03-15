package expression.generic;

public class Add<T extends Number> extends BinaryOperation<T> {

    public Add(GenericExpression<T> x, GenericExpression<T> y) {
        super(x, y);
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
    protected GenericNumber<T> calculate(GenericNumber<T> x, GenericNumber<T> y) {
        return x.add(y.getValue());
    }
}
