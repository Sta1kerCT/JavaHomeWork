package expression.generic;

public class Abs<T extends Number> extends UnaryOperation<T> {
    public Abs(GenericExpression<T> x) {
        super(x);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    protected String operationString() {
        return "abs";
    }

    @Override
    protected GenericNumber<T> calculate(GenericNumber<T> x) {
        return x.abs();
    }
}
