package expression.generic;

public abstract class GenericNumber<T extends Number> {
    protected final T x;

    public GenericNumber(T x) {
        this.x = x;
    }

    protected abstract T getValue();

    protected abstract GenericNumber<T> constructor(int y);

    protected abstract GenericNumber<T> add(T y);

    protected abstract GenericNumber<T> subtract(T y);

    protected abstract GenericNumber<T> divide(T y);

    protected abstract GenericNumber<T> multiply(T y);

    protected abstract GenericNumber<T> mod(T y);

    protected abstract GenericNumber<T> negate();

    protected abstract GenericNumber<T> abs();

    protected abstract GenericNumber<T> square();

}

