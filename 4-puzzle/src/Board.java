import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class Board {

    private final int[][] boardTiles;
    public Board (int[][] tiles) {
        boardTiles = tiles;
    }

    /**
     * String representation of the board
     * First line: N in NxN board
     * Subsequent Lines: The tile contents
     * @return The string representation of the board
     */
    public String toString() {
        StringBuilder str = new StringBuilder(boardTiles.length + "\n");

        for (int[] row : boardTiles) {
            for (int element : row) {
                str.append(" ").append(element);
            }
            str.append("\n");
        }
        return str.toString();
    }

    /**
     * Gets the N dimension of an NxN board
     * @return N of NxN board
     */
    public int dimension() {
        return boardTiles.length;
    }

    /**
     * Number of tiles out of place
     * @return The number of tiles out of place
     */
    public int hamming() {
        int hammingNum = 0;
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (getGoalFromIndex(i, j) == 0) continue;
                if (boardTiles[i][j] != getGoalFromIndex(i, j)) {
                    hammingNum++;
                }
            }
        }
        return hammingNum;
    }

    /**
     * Sum of manhattan distances between tiles and goal
     * Manhattan distance: The sum of the difference to goal of both the row and column
     * @return manhattan distance sum
     */
    public int manhattan() {
        int totalManhattan = 0;

        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles.length; j++) {
                if (i == boardTiles.length - 1 && j == boardTiles[i].length - 1) continue;
                int goal = getGoalFromIndex(i, j);
                if (goal == 0) continue;
                boolean found = false;
                for (int k = 0; k < boardTiles.length; k++) {
                    for (int l = 0; l < boardTiles.length; l++) {
                        if (goal == boardTiles[k][l]) {
                            totalManhattan += Math.abs((k - i)) + Math.abs((l - j));
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
            }
        }
        return totalManhattan;
    }

    /**
     * Gets the integer that should be in place (i, j) in the goal solution
     * @param i The row
     * @param j The column
     * @return The element that should be in (i, j) in the goal solution
     */
    private int getGoalFromIndex(int i, int j) {
        if (i == boardTiles.length - 1 && j == boardTiles[0].length - 1) return 0;
        int n = boardTiles.length;
        return ( (n * i + j) + 1);
    }

    public static void main(String[] args) {
        String currentDirectory = System.getProperty("user.dir");
        StdOut.print(currentDirectory);
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }


            // solve the slider puzzle
            Board board = new Board(tiles);

            StdOut.println("----BOARD TESTING----");
            StdOut.println("Test 1: toString() displays correctly");
            StdOut.println(board.toString());
            StdOut.println();

            StdOut.println("Test 2: dimensions are correct");
            StdOut.println("\tDimensions: " + board.dimension());
            StdOut.println();


            StdOut.println("Test 3: hamming()");
            StdOut.println("\thamming: " + board.hamming());
            StdOut.println();


            StdOut.println("Test 4: manhattan()");
            StdOut.println("\tmanhattan: " + board.manhattan());
            StdOut.println();

        }
    }
}
