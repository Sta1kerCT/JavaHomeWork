package expression;

public interface PriorityExpression extends Expression, DoubleExpression, TripleExpression {
    int getPriority();
    boolean uniqueCommutative();
}
