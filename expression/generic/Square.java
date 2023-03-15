package expression.generic;

public class Square<T extends Number> extends UnaryOperation<T> {
    public Square(GenericExpression<T> x) {
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
        return "square";
    }

    @Override
    protected GenericNumber<T> calculate(GenericNumber<T> x) {
        return x.square();
    }
}
