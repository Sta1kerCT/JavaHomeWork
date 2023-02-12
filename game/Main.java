package game;


public class Main {
    private static int[] diagonalObstructions(int m) {
        int[] obstructions = new int[4 * m];
        for (int i = 0; i < 2 * m; i += 2) {
            obstructions[i] = i / 2;
            obstructions[i + 1] = i / 2;
        }
        for (int i = m; i < 2 * m; i += 1) {
            obstructions[2 * i] = i - m;
            obstructions[2 * i + 1] = 2 * m - 1 - i;
        }
        return obstructions;
    }

    public static void main(String[] args) {
//        int[] obstructions = {1, 1, 0, 2};
//        final Tournament tournament = new Tournament(3);
//        tournament.play(new MnkBoard(11, 11, 3, diagonalObstructions(11)));
//        System.out.println(tournament);
        final Game game = new Game(true, new HumanPlayer(), new HumanPlayer());
        int result;
        int m = 5, n = 4, k = 4;
        do {
            result = game.play(new MnkBoard(m,  n, k));
        } while (result < 0);
        System.out.print("Game result: ");
        if (result == 1) {
            System.out.println("X`s win");
        } else if (result == 2) {
            System.out.println("O`s win");
        } else {
            System.out.println("Draw");
        }
    }
}
