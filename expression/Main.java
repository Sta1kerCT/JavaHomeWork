package expression;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Clear(
                new Variable("x"),
                new Const(0)
        ).evaluate(3));
    }
}
