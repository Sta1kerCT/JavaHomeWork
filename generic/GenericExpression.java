package expression.generic;

public interface GenericExpression<T extends Number> {
    GenericNumber<T> evaluate(GenericNumber<T> x, GenericNumber<T> y, GenericNumber<T> z);

    int getPriority();

    boolean uniqueCommutative();

    String toMiniString();
}
