package expression;

import java.util.Objects;

public abstract class UnaryOperation implements PriorityExpression {
    protected final PriorityExpression x;

    public UnaryOperation(PriorityExpression x) {
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
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int value) {
        return calculate(this.x.evaluate(value));
    }

    @Override
    public int evaluate(int value1, int value2, int value3) {
        return calculate(this.x.evaluate(value1, value2, value3));
    }

    protected abstract String operationString();
    protected abstract int calculate(int x);
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnaryOperation that = (UnaryOperation) o;
        return Objects.equals(x, that.x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }
}
