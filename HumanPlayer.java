package game;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position) {
        while (true) {
            out.println("m = " +  position.getM() + ", n = " + position.getN() + ", k = " + position.getK());
            out.println("Position");
            out.println(position);
            out.println(position.getTurn() + "'s move");
            out.println("Enter row and column");
            String row = in.next(), col = in.next();
            int rowInt, colInt;
            try {
                rowInt = Integer.parseInt(row);
                colInt = Integer.parseInt(col);
            } catch (NumberFormatException e) {
                out.println("Your row or column isn`t int");
                continue;
            }
            return new Move(rowInt, colInt, position.getTurn());
        }
    }
}
