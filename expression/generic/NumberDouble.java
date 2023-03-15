package expression.generic;

public class NumberDouble extends GenericNumber<Double> {
    public NumberDouble(Double x) {
        super(x);
    }

    @Override
    protected GenericNumber<Double> add(Double y) {
        return new NumberDouble(this.x + y);
    }

    @Override
    protected GenericNumber<Double> subtract(Double y) {
        return new NumberDouble(this.x - y);
    }

    @Override
    protected GenericNumber<Double> divide(Double y) {
        return new NumberDouble(this.x / y);
    }

    @Override
    protected GenericNumber<Double> multiply(Double y) {
        return new NumberDouble(this.x * y);
    }

    @Override
    protected GenericNumber<Double> negate() {
        return new NumberDouble(-this.x);
    }

    @Override
    protected Double getValue() {
        return x;
    }

    @Override
    protected GenericNumber<Double> mod(Double y) {
        return new NumberDouble(x % y);
    }

    @Override
    protected GenericNumber<Double> abs() {
        if (x > 0) {
            return new NumberDouble(x);
        } else if (x == 0) {
            return new NumberDouble(0.0);
        } else {
            return new NumberDouble(-x);
        }
    }

    @Override
    protected GenericNumber<Double> square() {
        return new NumberDouble(x * x);
    }

    @Override
    protected GenericNumber<Double> constructor(int y) {
        return new NumberDouble((double) y);
    }
}
