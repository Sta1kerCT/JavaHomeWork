package expression.generic;

public class Mod<T extends Number> extends BinaryOperation<T> {

    public Mod(final GenericExpression<T> x, final GenericExpression<T> y) {
        super(x, y);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    protected String getOperationString() {
        return "mod";
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
        return x.mod(y.getValue());
    }
}
