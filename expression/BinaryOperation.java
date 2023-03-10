package expression;

import java.util.Objects;

public abstract class BinaryOperation implements PriorityExpression {
    protected final PriorityExpression x;
    protected final PriorityExpression y;

    public BinaryOperation(final PriorityExpression x, final PriorityExpression y) {
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

    private String wrapped(final PriorityExpression x, boolean nonCommutativeY) {
        boolean inequality = nonCommutativeY ? getPriority() <= x.getPriority(
        ) : getPriority() < x.getPriority();
        return inequality ? "(" + x.toMiniString() + ")" : x.toMiniString();
    }
    @Override
    public double evaluate(double value) {
        return calculate(x.evaluate(value), y.evaluate(value));
    }

    @Override
    public int evaluate(int value) {
        return calculate(x.evaluate(value), y.evaluate(value));
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return calculate(x.evaluate(value1, value2, value3), y.evaluate(value1, value2, value3));
    }

    protected abstract double calculate(double x, double y);
    protected abstract int calculate(int x, int y);
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
