package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {
    private int iterator = -1;
    private String expression;

    private TripleExpression createBinOperation(String operationString, PriorityExpression leftExpression) {
        return switch (operationString) {
            case "set" -> new Set(leftExpression, (PriorityExpression) plusMinus());
            case "clear" -> new Clear(leftExpression, (PriorityExpression) plusMinus());
            case "+" -> new Add(leftExpression, (PriorityExpression) mulDiv());
            case "-" -> new Subtract(leftExpression, (PriorityExpression) mulDiv());
            case "*" -> new Multiply(leftExpression, (PriorityExpression) unaryOperation());
            case "/" -> new Divide(leftExpression, (PriorityExpression) unaryOperation());
            default -> throw new RuntimeException("Unexpected operation string at position " + iterator);
        };
    }

    private TripleExpression binaryOperation(int priority) {
        String[] operationStrings = switch (priority) {
            case 3 -> new String[]{"set", "clear"};
            case 2 -> new String[]{"+", "-"};
            case 1 -> new String[]{"*", "/"};
            default -> throw new RuntimeException("Unexpected operation string at position " + iterator);
        };
        PriorityExpression currentExpression;
        if (priority == 1) {
            currentExpression = (PriorityExpression) unaryOperation();
        } else {
            currentExpression = (PriorityExpression) binaryOperation(priority - 1);
        }
        while (true) {
            if (iterator >= expression.length() - 1) {
                return currentExpression;
            }
            iterator++;
            boolean operationNotFound = true;
            outer:
            for (String operationString : operationStrings) {
                for (int j = 0; j < operationString.length(); j++) {
                    if (expression.charAt(iterator + j) != operationString.charAt(j)) {
                        continue outer;
                    }
                }
                iterator += operationString.length() - 1;
                currentExpression = (PriorityExpression) createBinOperation(operationString,
                        currentExpression);
                operationNotFound = false;
            }
            if (operationNotFound && !Character.isWhitespace(expression.charAt(iterator))) {
                iterator--;
                return currentExpression;
            }
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
            if (iterator >= expression.length() - 1) {
                throw new RuntimeException("Your expression isn't valid! Your expression should end differently.");
            }
            iterator++;
            char curChar = expression.charAt(iterator);
            if (curChar == 'c' && expression.startsWith("count", iterator)) {
                iterator += 4;
                return new Count((PriorityExpression) unaryOperation());
            } else if (curChar == '-') {
				// :NOTE: в интерфейсе Г.А. есть реализованный за вас between
                if ('0' < expression.charAt(iterator + 1) && expression.charAt(iterator + 1) <= '9') {
                    iterator--;
                    return numberOrExpr();
                } else {
                    return new UnaryMinus((PriorityExpression) unaryOperation());
                }
            } else if (!Character.isWhitespace(curChar)) {
                iterator--;
                return numberOrExpr();
            }
        }
    }

    private TripleExpression numberOrExpr() {
        while (true) {
            iterator++;
            char curChar = expression.charAt(iterator);
            if (curChar == 'x' || curChar == 'y' || curChar == 'z') {
                return new Variable(String.valueOf(curChar));
			} else if (('0' <= curChar && curChar <= '9') || curChar == '-') {
                StringBuilder number = new StringBuilder();
                do {
                    number.append(curChar);
                    iterator++;
                    if (iterator >= expression.length()) {
                        break;
                    }
                    curChar = expression.charAt(iterator);
                } while ('0' <= curChar && curChar <= '9');
                iterator--;
                return new Const(Integer.parseInt(number.toString()));
            } else if (curChar == '(') {
                PriorityExpression currentExpression = (PriorityExpression) setClear();
                if (expression.charAt(++iterator) != ')') {
                    throw new RuntimeException("Unexpected token: " + curChar + " at position " + iterator);
                }
                return currentExpression;
            } else if (!Character.isWhitespace(curChar)) {
                throw new RuntimeException("Unexpected token: " + curChar + " at position " + iterator);
            }
        }
    }

    @Override
    public TripleExpression parse(String expression) {
        this.expression = expression;
        if (expression.length() != 0) {
            iterator = -1;
            return setClear();
        } else {
            return null;
        }
    }
}
