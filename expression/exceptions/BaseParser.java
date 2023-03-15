package expression.exceptions;

public abstract class BaseParser {
    public static final char END = 0;
    protected CharSource source;
    protected char curChar;

    public BaseParser(CharSource source) {
        this.source = source;
        take();
    }
    public BaseParser() {
    }

    protected char take() {
        final char result = curChar;
        curChar = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean take(char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean expect(char expected) {
        if (!take(expected)) {
            return false;
        }
        return true;
    }

    protected IllegalArgumentException error(String message) {
        return source.error(message);
    }

    protected boolean expect(String expected) {
        for (int i = 0; i < expected.length(); i++) {
            if (!expect(expected.charAt(i))) {
                source.back(i + 1);
                take();
                return false;
            }
        }
        return true;
    }

    protected boolean eof() {
        return curChar == END;
    }

    protected boolean test(final char expected) {
        return curChar == expected;
    }

    protected boolean checkEOF() {
        if (!eof()) {
            throw error("Expected EOF, found '" + curChar + "'");
        }
        return true;
    }

    protected boolean between(final char from, final char to) {
        return from <= curChar && curChar <= to;
    }
}
