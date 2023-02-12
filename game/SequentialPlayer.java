package game;


public class SequentialPlayer implements Player {
    @Override
    public Move move(final Position position) {
        int m = position.getM();
        int n = position.getN();
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}
