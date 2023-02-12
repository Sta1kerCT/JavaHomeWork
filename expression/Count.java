package expression;

public class Count extends UnaryOperation {
    public Count(PriorityExpression x) {
        super(x);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    protected int calculate(int x) {
        return count(Integer.toBinaryString(x));
    }
    private int count(String binaryString) {
        int answer = 0;
        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') {
                answer++;
            }
        }
        return answer;
    }

    @Override
    public boolean uniqueCommutative() {
        return false;
    }

    @Override
    protected String operationString() {
        return "count";
    }
}
