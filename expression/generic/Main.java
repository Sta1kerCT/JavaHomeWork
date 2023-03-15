package expression.generic;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        String mode = args[0];
        String expression = args[1];
        GenericNumber<? extends Number> genericNumber = switch (mode) {
            case "i" -> new NumberInt(0);
            case "bi" -> new NumberBI(BigInteger.valueOf(0));
            case "d" -> new NumberDouble((double) 0);
            case "u" -> new NumberUncheckedInt(0);
            case "l" -> new NumberLong(0L);
            case "s" -> new NumberShort((short) 0);
            default -> throw new IllegalArgumentException("Unexpected mode");
        };
        GenericTabulator genericTabulator = new GenericTabulator();
        Object[][][] result = genericTabulator.genericTabulate(genericNumber, expression,
                -2, 2, -2, 2, -2, 2);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.println("x = " + (i - 2) + ", y = " + (j - 2) + ", z = " + (k - 2) +
                            ", expr = " + expression + ", res = " + result[i][j][k]);
                }
            }
        }
    }
}
