package expression.generic;

public class NumberShort extends GenericNumber<Short> {
    NumberShort(Short x) {
        super(x);
    }

    @Override
    protected Short getValue() {
        return x;
    }

    @Override
    protected GenericNumber<Short> constructor(int y) {
        return new NumberShort((short) y);
    }

    @Override
    protected GenericNumber<Short> add(Short y) {
        return new NumberShort((short) (x + y));
    }

    @Override
    protected GenericNumber<Short> subtract(Short y) {
        return new NumberShort((short) (x - y));
    }

    @Override
    protected GenericNumber<Short> divide(Short y) {
        return new NumberShort((short) (x / y));
    }

    @Override
    protected GenericNumber<Short> multiply(Short y) {
        return new NumberShort((short) (x * y));
    }

    @Override
    protected GenericNumber<Short> negate() {
        return new NumberShort((short) (-x));
    }

    @Override
    protected GenericNumber<Short> mod(Short y) {
        return new NumberShort((short) (x % y));
    }

    @Override
    protected GenericNumber<Short> square() {
        return new NumberShort((short) (x * x));
    }

    @Override
    protected GenericNumber<Short> abs() {
        return new NumberShort(x >= 0 ? x : (short) -x);
    }
}
