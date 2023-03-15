package expression.generic;

import java.util.Objects;

public abstract class UnaryOperation<T extends Number> implements GenericExpression<T> {
    protected final GenericExpression<T> x;

    public UnaryOperation(GenericExpression<T> x) {
        this.x = x;
    }

    @Override
    public String toMiniString() {
        return operationString() + (x.getPriority() > 0 ? "(" + x.toMiniString() + ")" : " " + x.toMiniString());
    }

    @Override
    public String toString() {
        return operationString() + "(" + x + ")";
    }


    @Override
    public GenericNumber<T> evaluate(GenericNumber<T> value1, GenericNumber<T> value2, GenericNumber<T> value3) {
        return calculate(this.x.evaluate(value1, value2, value3));
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    protected abstract String operationString();

    protected abstract GenericNumber<T> calculate(GenericNumber<T> x);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UnaryOperation that = (UnaryOperation) o;
        return Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }
}
