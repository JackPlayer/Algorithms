import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode>  pq;
    private SearchNode delNode;
    private int moves;
    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int moves;
        private final int priority;

        SearchNode(Board board, SearchNode prev) {
            this.board = board;
            if (prev == null) {
                moves = 0;
            } else {
                moves = prev.getMoves() + 1;
            }
            this.prev = prev;
            priority = manhattanPriority();
        }

        public Board getBoard() {
            return this.board;
        }

        public int getPriority() {
            return priority;
        }

        public int getMoves() {
            return moves;
        }

        private int hammingPriority() {
            return board.hamming() + moves;
        }

        private int manhattanPriority() {
            return board.manhattan() + moves;
        }

        @Override
        public int compareTo(SearchNode o) {
            return this.getPriority() - o.getPriority();
        }
    }

    /**
     * Finds a solution for the initial board using the A* algorithm
     * @param initial The initial board to solve
     */
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("The initial game board cannot be null");
        pq = new MinPQ<SearchNode>();
        SearchNode initialBoard = new SearchNode(initial, null);
        pq.insert(initialBoard);

        delNode = pq.delMin();
        while (!delNode.getBoard().isGoal()) {
            Iterable<Board> neighbours = delNode.getBoard().neighbors();

            for (Board neighbour : neighbours) {
                if (!delNode.getBoard().equals(neighbour)) {
                    pq.insert(new SearchNode(neighbour, delNode));

                }
            }
            moves ++;

            delNode = pq.delMin();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    /**
     * Gets the solution to the puzzle
     * @return The shortest sequence of boards to solve the puzzle
     */
    public Iterable<Board> solution() {
        Stack<Board> boardStack = new Stack<Board>();
        SearchNode currNode = delNode;
        do {
            boardStack.push(currNode.getBoard());
            currNode = currNode.prev;
        } while (currNode != null);

        return boardStack;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
