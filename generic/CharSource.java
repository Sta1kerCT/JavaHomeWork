package expression.generic;

public interface CharSource {
    boolean hasNext();

    char next();

    char back(int x);

    IllegalArgumentException error(String message);

    String expr();
}
