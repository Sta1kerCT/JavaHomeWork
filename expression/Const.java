package expression;

import java.util.Objects;

public class Const implements PriorityExpression {
    private final double value;
    private final boolean isDouble;

    public Const(final int value) {
        this.value = value;
        this.isDouble = false;
    }

    public Const(final double value) {
        this.value = value;
        this.isDouble = true;
    }


    @Override
    public String toString() {
        if (isDouble) {
            return String.valueOf(value);
        } else {
            return String.valueOf((int) value);
        }
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    public String toMiniString() {
        if (isDouble) {
            return String.valueOf(value);
        } else {
            return String.valueOf((int) value);
        }
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return (int) value;
    }

    @Override
    public double evaluate(final double x) {
        return value;
    }

    @Override
    public int evaluate(final int x) {
        return (int) value;
    }

    public int getPriority() {
        return -1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Const that = (Const) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
