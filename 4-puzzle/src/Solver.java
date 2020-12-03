import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode delNode;
    private int moves;
    private final boolean solvable;

    private static class SearchNode implements Comparable<SearchNode> {
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

        private int manhattanPriority() {
            return board.manhattan() + moves;
        }

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
        SearchNode delNodeTwin;
        MinPQ<SearchNode>  pq;
        MinPQ<SearchNode> pqTwin;


        pq = new MinPQ<>();
        pqTwin = new MinPQ<>();

        SearchNode initialBoard = new SearchNode(initial, null);
        SearchNode initialTwinBoard = new SearchNode(initial.twin(), null);

        pq.insert(initialBoard);
        pqTwin.insert(initialTwinBoard);

        delNode = pq.delMin();
        delNodeTwin = pqTwin.delMin();

        while (!delNode.getBoard().isGoal() && !delNodeTwin.getBoard().isGoal()) {
            Iterable<Board> neighbours = delNode.getBoard().neighbors();
            Iterable<Board> neighboursTwin = delNodeTwin.getBoard().neighbors();

            for (Board neighbour : neighbours) {
                if (!delNode.getBoard().equals(neighbour)) {
                    pq.insert(new SearchNode(neighbour, delNode));
                }
            }
            for (Board neighbour : neighboursTwin) {
                if (!delNodeTwin.getBoard().equals(neighbour)) {
                    pqTwin.insert(new SearchNode(neighbour, delNodeTwin));

                }
            }

            moves ++;
            delNodeTwin = pqTwin.delMin();
            delNode = pq.delMin();
        }
        solvable = delNode.getBoard().isGoal();
    }

    /**
     * Is the initial board solvable?
     * @return true if it is solvable
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Minimum number of moves to solve the puzzle
     * @return The number of moves to solve. -1 if the puzzle is not solvable
     */
    public int moves() {
        if (!solvable) return -1;
        return this.moves;
    }

    /**
     * Gets the solution to the puzzle
     * @return The shortest sequence of boards to solve the puzzle
     */
    public Iterable<Board> solution() {
        if (!solvable) return null;
        Stack<Board> boardStack = new Stack<>();
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
