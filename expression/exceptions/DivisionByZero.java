package expression.exceptions;

public class DivisionByZero extends ArithmeticException {
    DivisionByZero() {
        super("division by zero");
    }
}
