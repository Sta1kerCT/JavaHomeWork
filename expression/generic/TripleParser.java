package expression.generic;


public interface TripleParser<T extends Number> {
    GenericExpression<T> parse(String expression, MySupplier<T> supplier) throws Exception;
}
