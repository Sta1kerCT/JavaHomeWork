package expression;

import java.util.Objects;

public class Variable implements PriorityExpression {
    private final String variable;

    public Variable(final String variable) {
        this.variable = variable;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public String toMiniString() {
        return variable;
    }

    @Override
    public int evaluate(final int x) {
        return x;
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        if (Objects.equals(variable, "x")) {
            return x;
        } else if (Objects.equals(variable, "y")) {
            return y;
        } else {
            return z;
        }
    }

    @Override
    public double evaluate(final double x) {
        return x;
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    public int getPriority() {
        return -1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Variable that = (Variable) o;
        return Objects.equals(variable, that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }
}
