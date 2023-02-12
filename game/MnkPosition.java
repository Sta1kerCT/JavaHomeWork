package game;

import java.util.Map;

public class MnkPosition implements Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.L, 'L'
    );
    private final Board board;
    private final int m;
    private final int n;
    private final int k;
    private final Cell[][] cells;

    public MnkPosition(Board board) {
        this.board = board;
        this.m = board.getM();
        this.n = board.getN();
        this.k = board.getK();
        this.cells = board.getCells();
    }

    @Override
    public boolean isValid(Move move) {
        return board.isValid(move);
    }

    @Override
    public int getM() {
        return m;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getK() {
        return k;
    }

    @Override
    public Cell getTurn() {
        return board.getTurn();
    }

    @Override
    public Cell getCell(int r, int c) {
        return board.getCell();
    }

    @Override
    public int[] getObstructions() {
        return board.getObstructions();
    }
    @Override
    public String toString() {
        StringBuilder alignment = new StringBuilder(" ");
        int maxParameter = Math.max(m, n);
        if (maxParameter > 10) {
            alignment.append(" ");
        }
        final StringBuilder sb = new StringBuilder(" " + alignment);
        for (int i = 0; i < n; i++) {
            sb.append(alignment);
            if (i >= 10){
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(i);
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n");
            sb.append(alignment);
            if (r >= 10) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(r);
            for (int c = 0; c < n; c++) {
                sb.append(alignment).append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
