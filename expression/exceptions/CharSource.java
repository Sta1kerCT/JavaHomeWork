package expression.exceptions;

public interface CharSource {
    boolean hasNext();
    char next();
    char back(int x);
    IllegalArgumentException error(String message);
    String expr();
}
