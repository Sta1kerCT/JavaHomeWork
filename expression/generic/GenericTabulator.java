package expression.generic;

import java.math.BigInteger;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression,
                                 int x1, int x2, int y1, int y2, int z1, int z2) throws IllegalStateException,
            ArithmeticException {
        GenericNumber<? extends Number> genericNumber = switch (mode) {
            case "i" -> new NumberInt(0);
            case "bi" -> new NumberBI(BigInteger.valueOf(0));
            case "d" -> new NumberDouble((double) 0);
            case "u" -> new NumberUncheckedInt(0);
            case "l" -> new NumberLong(0L);
            case "s" -> new NumberShort((short) 0);
            default -> throw new IllegalArgumentException("Unexpected mode");
        };
        return genericTabulate(genericNumber, expression, x1, x2, y1, y2, z1, z2);
    }

    public <T extends Number> Object[][][] genericTabulate(
            GenericNumber<T> genericNumber, String expression,
            int x1, int x2, int y1, int y2, int z1, int z2) throws Overflow {
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        ExpressionParser<T> parser = new ExpressionParser<>();
        MySupplier<T> supplier = () -> genericNumber;
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    try {
                        result[i][j][k] = parser.parse(expression, supplier).
                                evaluate(supplier.get().constructor(x1 + i),
                                        supplier.get().constructor(y1 + j),
                                        supplier.get().constructor(z1 + k)).getValue();
                    } catch (ArithmeticException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }
}
