package expression.generic;


public class ExpressionParser<T extends Number> extends BaseParser<T> implements TripleParser<T> {
    private MySupplier<T> supplier;

    public ExpressionParser(CharSource source, MySupplier<T> supplier) {
        super(source);
        this.supplier = supplier;
    }

    public ExpressionParser() {
        super();
    }

    private GenericExpression<T> createBinOperation(String operationString, GenericExpression<T> leftExpression) {
        return switch (operationString) {
            case "+" -> new Add<>(leftExpression, firstPriority());
            case "-" -> new Subtract<>(leftExpression, firstPriority());
            case "*" -> new Multiply<>(leftExpression, unaryOperation());
            case "/" -> new Divide<>(leftExpression, unaryOperation());
            case "mod" -> new Mod<>(leftExpression, unaryOperation());
            default -> throw error("Unexpected operation string: " + operationString);
        };
    }

    private GenericExpression<T> binaryOperation(int priority) {
        String[] operationStrings = switch (priority) {
            case 2 -> new String[]{"+", "-"};
            case 1 -> new String[]{"*", "/", "mod"};
            default -> throw error("Unexpected priority:" + priority);
        };
        GenericExpression<T> currentExpression;
        if (priority == 1) {
            currentExpression = unaryOperation();
        } else {
            currentExpression = binaryOperation(priority - 1);
        }
        while (true) {
            skipWhitespace();
            if (eof()) {
                return currentExpression;
            }
            boolean operationNotFound = true;
            for (String operationString : operationStrings) {
                if (!expect(operationString)) {
                    continue;
                }
                currentExpression = createBinOperation(operationString,
                        currentExpression);
                operationNotFound = false;
            }
            if (operationNotFound) {
                return currentExpression;
            }
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(curChar)) {
            take();
        }
    }

    private GenericExpression<T> secondPriority() {
        return binaryOperation(2);
    }

    private GenericExpression<T> firstPriority() {
        return binaryOperation(1);
    }

    private GenericExpression<T> createUnaryOperation(String operationString) {
        return switch (operationString) {
            case "abs" -> new Abs<>(unaryOperation());
            case "square" -> new Square<>(unaryOperation());
            case "-" -> new UnaryMinus<>(unaryOperation());
            default -> throw error("Unexpected operation string: " + operationString);
        };
    }

    private GenericExpression<T> unaryOperation() {
        skipWhitespace();
        String[] operationStrings = new String[]{"-", "abs", "square"};
        for (String operationString : operationStrings) {
            if (!expect(operationString)) {
                continue;
            }
            if (operationString.equals("-")) {
                if (between('0', '9')) {
                    source.back(2);
                    take();
                    return numberOrExpr();
                }
            }
            if (!eof()) {
                return createUnaryOperation(operationString);
            } else {
                throw error("This operation must have an argument!");
            }
        }
        return numberOrExpr();
    }

    private GenericExpression<T> numberOrExpr() {
        skipWhitespace();
        if (test('x') || test('y') || test('z')) {
            GenericExpression<T> result = new Variable<>(String.valueOf(curChar));
            take();
            return result;
        } else if (between('0', '9') || test('-')) {
            StringBuilder number = new StringBuilder();
            do {
                number.append(curChar);
                take();
                if (eof()) {
                    break;
                }
            } while (between('0', '9'));
            return new Const<>(supplier.get().constructor(Integer.parseInt(number.toString())));
        } else if (expect('(')) {
            GenericExpression<T> currentExpression = secondPriority();
            if (!expect(')')) {
                throw error("Unexpected token: " + curChar);
            }
            return currentExpression;
        } else {
            throw error("Unexpected token: " + curChar);
        }
    }

    @Override
    public GenericExpression<T> parse(String expression, MySupplier<T> supplier) {
        return parse(new StringCharSource(expression), supplier);
    }

    public GenericExpression<T> parse(final CharSource expression, MySupplier<T> supplier) {
        return new ExpressionParser<>(expression, supplier).parseExpression();
    }

    public GenericExpression<T> parseExpression() {
        final GenericExpression<T> result = secondPriority();
        checkEOF();
        return result;
    }
}

