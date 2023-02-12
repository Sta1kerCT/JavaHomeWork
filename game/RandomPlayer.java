package game;

import java.util.Random;


public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position) {
        int m = position.getM();
        int n = position.getN();
        while (true) {
            int r = random.nextInt(m);
            int c = random.nextInt(n);
            final Move move = new Move(r, c, position.getTurn());
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
