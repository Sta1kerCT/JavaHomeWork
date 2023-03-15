package expression.generic;

public class UnaryMinus<T extends Number> extends UnaryOperation<T> {
    public UnaryMinus(GenericExpression<T> x) {
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
        return "-";
    }

    @Override
    protected GenericNumber<T> calculate(GenericNumber<T> x) {
        return x.negate();
    }
}
