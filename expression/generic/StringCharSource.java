package expression.generic;


public class StringCharSource implements CharSource {
    private final String string;
    private int pos;

    public StringCharSource(String string) {
        this.string = string;
        pos = 0;
    }

    @Override
    public boolean hasNext() {
        return pos < string.length();
    }

    @Override
    public char next() {
        return string.charAt(pos++);
    }

    @Override
    public char back(int x) {
        pos -= x;
        return string.charAt(pos);
    }

    @Override
    public IllegalArgumentException error(String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }

    public String expr() {
        return string;
    }
}
