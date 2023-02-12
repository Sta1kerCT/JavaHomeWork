package game;


public interface Board {
    boolean isValid(Move move);
    int getM();
    int getN();
    int getK();
    Position getPosition();
    Cell getCell();
    Cell getTurn();
    Result makeMove(Move move);
    int[] getObstructions();
    Cell[][] getCells();
}
