package expression.generic;


public class NumberUncheckedInt extends GenericNumber<Integer> {
    public NumberUncheckedInt(Integer x) {
        super(x);
    }

    @Override
    protected GenericNumber<Integer> constructor(int y) {
        return new NumberUncheckedInt(y);
    }

    @Override
    protected GenericNumber<Integer> add(Integer y) {
        return new NumberUncheckedInt(this.x + y);
    }

    @Override
    protected GenericNumber<Integer> subtract(Integer y) {
        return new NumberUncheckedInt(this.x - y);
    }

    @Override
    protected GenericNumber<Integer> divide(Integer y) {
        return new NumberUncheckedInt(this.x / y);
    }

    @Override
    protected GenericNumber<Integer> multiply(Integer y) {
        return new NumberUncheckedInt(this.x * y);
    }

    @Override
    protected GenericNumber<Integer> negate() {
        return new NumberUncheckedInt(-this.x);
    }

    @Override
    protected GenericNumber<Integer> mod(Integer y) {
        return new NumberUncheckedInt(x % y);
    }

    @Override
    protected GenericNumber<Integer> abs() {
        return new NumberUncheckedInt(x >= 0 ? x : -x);
    }

    @Override
    protected GenericNumber<Integer> square() {
        return new NumberUncheckedInt(x * x);
    }

    @Override
    protected Integer getValue() {
        return x;
    }
}
