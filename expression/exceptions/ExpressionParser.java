package expression.exceptions;

import expression.*;

public class ExpressionParser extends BaseParser implements TripleParser {
    public ExpressionParser(CharSource source) {
        super(source);
    }
    public ExpressionParser() {
        super();
    }

    private TripleExpression createBinOperation(String operationString, PriorityExpression leftExpression) {
        return switch (operationString) {
            case "set" -> new CheckedSet(leftExpression, (PriorityExpression) plusMinus());
            case "clear" -> new CheckedClear(leftExpression, (PriorityExpression) plusMinus());
            case "+" -> new CheckedAdd(leftExpression, (PriorityExpression) mulDiv());
            case "-" -> new CheckedSubtract(leftExpression, (PriorityExpression) mulDiv());
            case "*" -> new CheckedMultiply(leftExpression, (PriorityExpression) unaryOperation());
            case "/" -> new CheckedDivide(leftExpression, (PriorityExpression) unaryOperation());
            default -> throw error("Unexpected operation string: " + operationString);
        };
    }

    private TripleExpression binaryOperation(int priority) {
        String[] operationStrings = switch (priority) {
            case 3 -> new String[]{"set", "clear"};
            case 2 -> new String[]{"+", "-"};
            case 1 -> new String[]{"*", "/"};
            default -> throw error("Unexpected priority:" + priority);
        };
        PriorityExpression currentExpression;
        if (priority == 1) {
            currentExpression = (PriorityExpression) unaryOperation();
        } else {
            currentExpression = (PriorityExpression) binaryOperation(priority - 1);
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
                if (priority == 3 && (between('0', '9') || between('a', 'z'))) {
                    throw error("Unexpected token: " + curChar);
                }
                currentExpression = (PriorityExpression) createBinOperation(operationString,
                        currentExpression);
                operationNotFound = false;
                break;
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

    private TripleExpression setClear() {
        return binaryOperation(3);
    }

    private TripleExpression plusMinus() {
        return binaryOperation(2);
    }

    private TripleExpression mulDiv() {
        return binaryOperation(1);
    }

    private TripleExpression unaryOperation() {
        while (true) {
            skipWhitespace();
            if (expect("count")) {
                if (between('0', '9') || between('x', 'z')) {
                    throw error("Unexpected token: " + curChar);
                }
                return new Count((PriorityExpression) unaryOperation());
            } else if (expect("log10")) {
                if (!eof()) {
                    if (between('0', '9') || between('a', 'z')) {
                        throw error("Unexpected token: " + curChar);
                    }
                    return new Log10((PriorityExpression) unaryOperation());
                } else {
                    throw error("This operation must have an argument!");
                }
            } else if (expect("pow10")) {
                if (!eof()) {
                    if (between('0', '9') || between('x', 'z')) {
                        throw error("Unexpected token: " + curChar);
                    }
                    return new Pow10((PriorityExpression) unaryOperation());
                } else {
                    throw error("This operation must have an argument!");
                }
            } else if (expect('-')) {
                if (between('0', '9')) {
                    source.back(2);
                    take();
                    return numberOrExpr();
                } else if (!eof()){
                    return new CheckedNegate((PriorityExpression) unaryOperation());
                } else {
                    throw error("This operation must have an argument!");
                }
            } else {
                return numberOrExpr();
            }
        }
    }

    private TripleExpression numberOrExpr() {
        while (true) {
            skipWhitespace();
            if (test('x') || test('y') || test('z')) {
                TripleExpression result = new Variable(String.valueOf(curChar));
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
                return new Const(Integer.parseInt(number.toString()));
            } else if (expect('(')) {
                PriorityExpression currentExpression = (PriorityExpression) setClear();
                if (!expect(')')) {
                    throw error("Unexpected token: " + curChar);
                }
                return currentExpression;
            } else {
                throw error("Unexpected token: " + curChar);
            }
        }
    }

    @Override
    public TripleExpression parse(String expression) {
        return parse(new StringCharSource(expression));
    }

    public TripleExpression parse(final CharSource expression) {
        return (TripleExpression) new ExpressionParser(expression).parseExpression();
    }
    public Object parseExpression() {
        final Object result = setClear();
        checkEOF();
        return result;
    }
}

