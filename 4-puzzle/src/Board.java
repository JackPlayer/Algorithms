import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public final class Board {

    private final int[][] boardTiles;
    public Board(int[][] tiles) {
        boardTiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, boardTiles[i], 0, tiles[i].length);
        }
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
        int n = boardTiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
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
        int n = boardTiles.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
               if (boardTiles[i][j] != 0) {
                   int targetX = (boardTiles[i][j] - 1) / n;
                   int targetY = (boardTiles[i][j] - 1) % n;
                   int dx = i - targetX;
                   int dy = j - targetY;
                   totalManhattan += Math.abs(dx) + Math.abs(dy);
               }
            }
        }
        return totalManhattan;
    }

    /**
     * Checks for equality between boards
     * @param y The board to check equality against
     * @return True if the board's have the same dimensions and their elements are the same.
     */
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        final Board that = (Board) y;

        if (!(this.dimension() == that.dimension())) return false;

        return Arrays.deepEquals(boardTiles, that.boardTiles);
    }

    /**
     * Is this board the goal board
     * @return true if this board is the goal board
     */
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    /**
     * Gets the neighbouring boards
     * @return An iterable object of the neighbouring boards
     */
    public Iterable<Board> neighbors() {
        Stack<Board> neighbours = new Stack<>();
        int openRow = -1;
        int openCol = -1;
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (boardTiles[i][j] == 0) {
                    openCol = j;
                    openRow = i;
                    break;
                }
            }
        }

        int[][] newBoardTop = newBoardNeighbour(openRow, openCol, openRow + 1, openCol);
        int[][] newBoardBottom = newBoardNeighbour(openRow, openCol, openRow - 1, openCol);
        int[][] newBoardLeft = newBoardNeighbour(openRow, openCol, openRow, openCol - 1);
        int[][] newBoardRight = newBoardNeighbour(openRow, openCol, openRow, openCol + 1);

        if (newBoardBottom.length > 0) {
            neighbours.push(new Board(newBoardBottom));
        }

        if (newBoardTop.length > 0) {
            neighbours.push(new Board(newBoardTop));
        }

        if (newBoardLeft.length > 0) {
            neighbours.push(new Board(newBoardLeft));
        }

        if (newBoardRight.length > 0) {
            neighbours.push(new Board(newBoardRight));
        }
        return neighbours;
    }

    /**
     * Creates a new twin board
     * Twin boards have two elements that are beside each other that have been swapped from the original board
     * @return The new twin board
     */
    public Board twin() {
        boolean swapped = false;
        int[][] twinInts = new int[boardTiles.length][boardTiles.length];
        for (int i = 0; i < boardTiles.length; i++) {
            System.arraycopy(boardTiles[i], 0, twinInts[i], 0, boardTiles.length);
        }
        for (int i = 0; i < twinInts.length; i++) {
            if (swapped) break;
            for (int j = 0; j < twinInts.length; j++) {
                boolean inBounds = (j+1 < twinInts[i].length);
                if (inBounds) {
                    if (twinInts[i][j] != 0 && twinInts[i][j + 1] != 0) {
                        int temp = twinInts[i][j+1];
                        twinInts[i][j+1] = twinInts[i][j];
                        twinInts[i][j] = temp;
                        swapped = true;
                        break;
                    }
                }
            }
        }
        return new Board(twinInts);
    }
    /**
     * Creates a new board by moving newRow, newCol into openRow, openCol
     * @param openRow The open row
     * @param openCol The open column
     * @param newRow The new row
     * @param newCol the new column
     *
     * @return The new neighbour board. If the swap cannot be performed null is returned
     */
    private int[][] newBoardNeighbour(int openRow, int openCol, int newRow, int newCol) {
        int[][] newBoard = new int[boardTiles.length][boardTiles.length];

        // Copy array
        for (int i = 0; i < newBoard.length; i++) {
            System.arraycopy(boardTiles[i], 0, newBoard[i], 0, newBoard[i].length);
        }
        try {
            newBoard[openRow][openCol] = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            return new int[0][0];
        }
        return newBoard;
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
        return ((n * i + j) + 1);
    }

    public static void main(String[] args) {
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
            Board boardEquals = new Board(tiles);

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

            StdOut.println("Text 5: equals()");
            StdOut.println("\tequals: " + board.equals(boardEquals));

            StdOut.println();
            StdOut.println("Text 6: neighbours()");
            for (Board neighbour : board.neighbors()) {

                StdOut.println(neighbour.toString());

            }

            StdOut.println();
            StdOut.println("Text 7: twin()");
            Board twin = board.twin();
            StdOut.println(twin.toString());


        }
    }
}
