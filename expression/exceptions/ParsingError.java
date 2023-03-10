package expression.exceptions;

public class ParsingError extends IllegalArgumentException {
    ParsingError(String s) {
        super(s);
    }
    ParsingError() {
        super("parsing error");
    }
}