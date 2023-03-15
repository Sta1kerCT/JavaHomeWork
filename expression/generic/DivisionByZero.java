package expression.generic;

public class DivisionByZero extends ArithmeticException {
    DivisionByZero() {
        super("division by zero");
    }
}
