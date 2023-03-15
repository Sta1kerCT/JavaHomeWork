package expression.generic;

import java.util.Objects;

public abstract class BinaryOperation<T extends Number> implements GenericExpression<T> {
    protected final GenericExpression<T> x;
    protected final GenericExpression<T> y;


    public BinaryOperation(final GenericExpression<T> x, final GenericExpression<T> y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + " " + getOperationString() + " " + y + ")";
    }

    @Override
    public String toMiniString() {
        if (isCommutative()) {
            return wrapped(x, false) + " " + getOperationString() + " " +
                    ((y.uniqueCommutative() && getPriority() == y.getPriority())
                            ? "(" + y.toMiniString() + ")" : wrapped(y, false));
        } else {
            return wrapped(x, false) + " " + getOperationString() + " " + wrapped(y, true);
        }
    }

    protected abstract String getOperationString();

    protected abstract boolean isCommutative();

    private String wrapped(final GenericExpression<T> x, boolean nonCommutativeY) {
        boolean inequality = nonCommutativeY ? getPriority() <= x.getPriority(
        ) : getPriority() < x.getPriority();
        return inequality ? "(" + x.toMiniString() + ")" : x.toMiniString();
    }

    @Override
    public GenericNumber<T> evaluate(GenericNumber<T> value1, GenericNumber<T> value2, GenericNumber<T> value3) {
        return calculate(x.evaluate(value1, value2, value3), y.evaluate(value1, value2, value3));
    }

    protected abstract GenericNumber<T> calculate(GenericNumber<T> x, GenericNumber<T> y);

    @Override
    public int hashCode() {
        return Objects.hash(x, y, getClass());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BinaryOperation operation = (BinaryOperation) o;
        return Objects.equals(x, operation.x) && Objects.equals(y, operation.y);
    }
}
