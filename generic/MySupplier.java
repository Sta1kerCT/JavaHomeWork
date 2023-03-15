package expression.generic;

import java.util.function.Supplier;

@FunctionalInterface
public interface MySupplier<T extends Number> extends Supplier<GenericNumber<T>> {
    GenericNumber<T> get();
}
