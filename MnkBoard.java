package game;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MnkBoard implements Board {
    private final Cell[][] cells;
    private final Position position;
    private Cell turn;
    private final int m;
    private final int n;
    private final int k;
    private int[] obstructions = new int[0];
    private int empty;


    public MnkBoard(int m, int n, int k) {
        this.m = m; // :NOTE: if you don't check arguments here, it may produce exception
        this.n = n;
        this.k = k;
        this.empty = m * n;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        position = new MnkPosition(this);
    }

    public MnkBoard(int m, int n, int k, int[] obstructions) { // :NOTE: it's a copypaste
        this.m = m;
        this.n = n;
        this.k = k;
        this.empty = m * n;
        this.obstructions = obstructions;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        for (int i = 0; i < obstructions.length; i += 2) {
            if (obstructions[i] >= 0 && obstructions[i] < m && obstructions[i + 1] >= 0 && obstructions[i + 1] < n) {
                cells[obstructions[i]][obstructions[i + 1]] = Cell.L;
                empty--;
            }
        }
        position = new MnkPosition(this);
    }


    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            System.out.println("You lost, because your move isn`t valid!");
            return Result.LOSE;
        }
        int row = move.getRow();
        int col = move.getColumn();
        cells[row][col] = move.getValue();
        if (rowColCheck(row, col) || diagonalsCheck(row, col)) {
            return Result.WIN;
        }
        if (--empty == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }


    public Cell getCell(final int r, final int c) {
        return cells[r][c];
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
        return turn;
    }

    @Override
    public Cell[][] getCells() {
        return cells;
    }

    @Override
    public int[] getObstructions() {
        return Arrays.copyOf(obstructions, obstructions.length); // :NOTE: I don't know why do you think that you need copy array here
    }


    private boolean rowColCheck(int row, int col) {
        int rowHitCounter = 0;
        int colHitCounter = 0;
        for (int i = 1 - k; i <= k - 1; i++) {
            if (row - i >= 0 && row - i < m) { // :NOTE: it's a copypaste
                if (cells[row - i][col] == turn) {
                    rowHitCounter++;
                } else {
                    rowHitCounter = 0;
                }
                if (rowHitCounter == k) {
                    return true;
                }
            }
            if (col - i >= 0 && col - i < n) {
                if (cells[row][col - i] == turn) {
                    colHitCounter++;
                } else {
                    colHitCounter = 0;
                }
                if (colHitCounter == k) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean diagonalsCheck(int row, int col) {
        int hitCounter1 = 0;
        int hitCounter2 = 0;
        int cellCounter = 0;
        for (int i = row - k + 1, j = k - 1; i < m && cellCounter < 2 * k - 1; i++, j--) {
            cellCounter++;
            if (i >= 0 && col - j >= 0 && col - j < n) { // :NOTE: it's a copypaste
                if (cells[i][col - j] == turn) {
                    hitCounter1++;
                } else {
                    hitCounter1 = 0;
                }
                if (hitCounter1 == k) {
                    return true;
                }
            }
            if (i >= 0 && col + j >= 0 && col + j < n) {
                if (cells[i][col + j] == turn) {
                    hitCounter2++;
                } else {
                    hitCounter2 = 0;
                }
                if (hitCounter2 == k) {
                    return true;
                }
            }
        }
        return false;
    }
}