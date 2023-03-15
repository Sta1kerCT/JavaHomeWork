package expression.generic;

public class NumberLong extends GenericNumber<Long> {
    public NumberLong(Long x) {
        super(x);
    }

    @Override
    protected Long getValue() {
        return x;
    }

    @Override
    protected GenericNumber<Long> constructor(int y) {
        return new NumberLong((long) y);
    }

    @Override
    protected GenericNumber<Long> add(Long y) {
        return new NumberLong(x + y);
    }

    @Override
    protected GenericNumber<Long> subtract(Long y) {
        return new NumberLong(x - y);
    }

    @Override
    protected GenericNumber<Long> divide(Long y) {
        return new NumberLong(x / y);
    }

    @Override
    protected GenericNumber<Long> abs() {
        return new NumberLong(x >= 0 ? x : -x);
    }

    @Override
    protected GenericNumber<Long> multiply(Long y) {
        return new NumberLong(x * y);
    }

    @Override
    protected GenericNumber<Long> mod(Long y) {
        return new NumberLong(x % y);
    }

    @Override
    protected GenericNumber<Long> square() {
        return new NumberLong(x * x);
    }

    @Override
    protected GenericNumber<Long> negate() {
        return new NumberLong(-x);
    }
}
