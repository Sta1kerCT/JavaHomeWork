package game;


public interface Position {
    boolean isValid(Move move);
    int getM();
    int getN();
    int getK();
    Cell getTurn();
    Cell getCell(int r, int c);
    int[] getObstructions();
}
