package expression.generic;


public class NumberInt extends GenericNumber<Integer> {
    public NumberInt(Integer x) {
        super(x);
    }

    @Override
    protected GenericNumber<Integer> add(Integer y) {
        if ((y > 0 && Integer.MAX_VALUE - y < x) || (y <= 0 && Integer.MIN_VALUE - y > x)) {
            throw new Overflow();
        }
        return new NumberInt(this.x + y);
    }

    @Override
    protected GenericNumber<Integer> subtract(Integer y) {
        if ((y <= 0 && Integer.MAX_VALUE + y < x) || (y > 0 && Integer.MIN_VALUE + y > x)) {
            throw new Overflow();
        }
        return new NumberInt(this.x - y);
    }

    @Override
    protected GenericNumber<Integer> divide(Integer y) {
        if (y == 0) {
            throw new DivisionByZero();
        } else if (x == Integer.MIN_VALUE && y == -1) {
            throw new Overflow();
        }
        return new NumberInt(this.x / y);
    }

    @Override
    protected GenericNumber<Integer> multiply(Integer y) {
        if ((y > 0 && (Integer.MIN_VALUE / y > x || Integer.MAX_VALUE / y < x)) ||
                (x > 0 && (Integer.MIN_VALUE / x > y || Integer.MAX_VALUE / x < y)) ||
                (y < 0 && (Integer.MAX_VALUE / y > x)) ||
                (x < 0 && (Integer.MAX_VALUE / x > y))) {
            throw new Overflow();
        }
        return new NumberInt(this.x * y);
    }

    @Override
    protected GenericNumber<Integer> negate() {
        if (x == Integer.MIN_VALUE) {
            throw new Overflow();
        }
        return new NumberInt(-this.x);
    }

    @Override
    protected GenericNumber<Integer> mod(Integer y) {
        if (y == 0) {
            throw new DivisionByZero();
        }
        return new NumberInt(x % y);
    }

    @Override
    protected GenericNumber<Integer> abs() {
        if (x == Integer.MIN_VALUE) {
            throw new Overflow();
        }
        return new NumberInt(x >= 0 && x != -0.0 ? x : -x);
    }

    @Override
    protected GenericNumber<Integer> square() {
        if ((x > 0 && Integer.MAX_VALUE / x < x) ||
                (x < 0 && (Integer.MAX_VALUE / x > x))) {
            throw new Overflow();
        }
        return new NumberInt(x * x);
    }

    @Override
    protected Integer getValue() {
        return x;
    }

    @Override
    protected GenericNumber<Integer> constructor(int y) {
        return new NumberInt(y);
    }
}
