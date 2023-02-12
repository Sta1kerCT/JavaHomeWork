package game;

public class Tournament {
    private final int[][] results;
    private final int numberOfPlayers;
    private final Player[] players;
    public Tournament(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.results = new int[numberOfPlayers][numberOfPlayers + 1];
        this.players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new HumanPlayer();
        }
    }


    public void play(MnkBoard tournamentBoard) {
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            for (int j = i + 1; j < numberOfPlayers; j++) {
                Game game;
                int firstPlayerNo = i;
                int secondPlayerNo = j;
                for (int gameNo = 0; gameNo < 2; gameNo++) {
                    if (gameNo == 1) {
                        firstPlayerNo = j;
                        secondPlayerNo = i;
                    }
                    game = new Game(false, players[firstPlayerNo], players[secondPlayerNo]);
                    System.out.println("Game: player" + firstPlayerNo + " with X vs player" + secondPlayerNo + " with" +
                            " O");
                    int result;
                    do {
                        result = game.play(new MnkBoard(
                                        tournamentBoard.getM(),
                                        tournamentBoard.getN(),
                                        tournamentBoard.getK(),
                                        tournamentBoard.getObstructions()
                                )
                        );
                    } while (result < 0);
                    System.out.print("Game result: ");
                    if (result == 1) {
                        pointsAdd(3 ,firstPlayerNo, secondPlayerNo);
                        System.out.println("player " + firstPlayerNo + " won");
                    } else if (result == 2) {
                        pointsAdd(3, secondPlayerNo, firstPlayerNo);
                        System.out.println("player " + secondPlayerNo + " won");
                    } else {
                        pointsAdd(1, secondPlayerNo, firstPlayerNo);
                        pointsAdd(1, firstPlayerNo, secondPlayerNo);
                        System.out.println("Draw");
                    }
                    System.out.println();
                }
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder table = new StringBuilder("  ");
        StringBuilder finalResults = new StringBuilder();
        for (int i = 0; i < numberOfPlayers; i++) {
            table.append(i).append(" ");
            finalResults.append("player").append(i).append(" scored ").append(results[i][numberOfPlayers]).append(" points\n");
        }
        table.append('\n');
        for (int i = 0; i < numberOfPlayers; i++) {
            table.append(i).append(" ");
            for (int j = 0; j < numberOfPlayers; j++) {
                if (j == i) {
                    table.append("- ");
                    continue;
                }
                table.append(results[i][j]).append(" ");
            }
            table.append('\n');
        }
        return finalResults + table.toString();
    }
    public void pointsAdd(int numberOfPoints, int winnerNo, int loserNo) {
        results[winnerNo][loserNo] += numberOfPoints;
        results[winnerNo][numberOfPlayers] += numberOfPoints;
    }

}
