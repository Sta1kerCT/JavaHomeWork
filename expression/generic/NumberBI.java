package expression.generic;

import java.math.BigInteger;

public class NumberBI extends GenericNumber<BigInteger> {
    NumberBI(BigInteger x) {
        super(x);
    }

    @Override
    protected BigInteger getValue() {
        return x;
    }

    @Override
    protected GenericNumber<BigInteger> add(BigInteger y) {
        return new NumberBI(this.x.add(y));
    }

    @Override
    protected GenericNumber<BigInteger> subtract(BigInteger y) {
        return new NumberBI(this.x.subtract(y));
    }

    @Override
    protected GenericNumber<BigInteger> divide(BigInteger y) {
        return new NumberBI(this.x.divide(y));
    }

    @Override
    protected GenericNumber<BigInteger> multiply(BigInteger y) {
        return new NumberBI(this.x.multiply(y));
    }

    @Override
    protected GenericNumber<BigInteger> negate() {
        return new NumberBI(x.negate());
    }

    @Override
    protected GenericNumber<BigInteger> mod(BigInteger y) {
        return new NumberBI(x.mod(y));
    }

    @Override
    protected GenericNumber<BigInteger> abs() {
        return new NumberBI(x.abs());
    }

    @Override
    protected GenericNumber<BigInteger> square() {
        return new NumberBI(x.multiply(x));
    }

    @Override
    protected GenericNumber<BigInteger> constructor(int y) {
        return new NumberBI(BigInteger.valueOf(y));
    }
}
