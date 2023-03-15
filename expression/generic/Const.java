package expression.generic;

import java.util.Objects;

public class Const<T extends Number> implements GenericExpression<T> {
    private final GenericNumber<T> value;

    public Const(final GenericNumber<T> value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    public GenericNumber<T> evaluate(GenericNumber<T> x, GenericNumber<T> y, GenericNumber<T> z) {
        return value;
    }

    @Override
    public String toMiniString() {
        return String.valueOf(value);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Const that = (Const) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
