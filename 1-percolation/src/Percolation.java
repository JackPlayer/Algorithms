import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation Simulation for Coursera Algorithms Course
 * https://www.coursera.org/learn/algorithms-part1
 * @author Jack Player
 */
public class Percolation {
    private final int gridSize;
    private final int bottom;
    private final int top;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF unionTree;

    private int openSites;

    /**
     * Percolation class with n x n grid, where initially all the sites are blocked.
     * @param n nxn grid to create
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid must be greater than 0");
        }

        unionTree = new WeightedQuickUnionUF((n*n) + 2);
        grid = new boolean[n][n];

        openSites = 0;
        gridSize = n;

        bottom = grid.length * grid.length + 1;
        top = 0;
    }

    /**
     * Opens the node at (row, col) if is is not already, and unions the node with other open nodes that
     * are above, below, right or left of it.
     * @param row The row to open (starting at 1)
     * @param col The column to open (starting at 1)
     */
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException("Row or column is out of bounds");
        }

        // Grid blocked?
        if (!grid[row-1][col-1]) {
            grid[row - 1][col - 1] = true;
            openSites++;
        }
        // Top row node connects to virtual top node
        if (row == 1) {
            int p = (row - 1) * gridSize + col;
            unionTree.union(p, top);
        }
        // Bottom row node connects to virtual bottom node
        if (row == gridSize) {
            int p = (row - 1) * gridSize + col;
            unionTree.union(p, bottom);
        }

        // Check above
        if (row > 1) {
            if (grid[row - 2][col - 1]) {
                int p = (row - 1) * gridSize + col;
                int q = (row - 2) * gridSize + col;
                unionTree.union(p, q);
            }

        }

        // Check Below
        if (row < gridSize) {
            if (grid[row][col - 1]) {
                int p = (row - 1) * gridSize + col;
                int q = (row) * gridSize + col;
                unionTree.union(p, q);
            }
        }

        // Check Right
        if (col < gridSize) {
            if (grid[row - 1][col]) {
                int p = (row - 1) * gridSize + col;
                int q = (row - 1) * gridSize + col + 1;
                unionTree.union(p, q);
            }
        }

        // Check Left
        if (col > 1) {
            if (grid[row - 1][col - 2]) {
                int p = (row - 1) * gridSize + col;
                int q = (row - 1) * gridSize + col - 1;
                unionTree.union(p, q);
            }
        }
    }

    /**
     * Checks if the node at (row, col) is open
     * @param row Row to check (starts at 1)
     * @param col Column to check (starts at 1)
     * @return true if the node at (row, col) is open
     */
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException("Row or column is out of bounds");
        }
        return grid[row-1][col-1];
    }

    /**
     * Checks if the node (row, col) is open and is unioned with the top node
     * @param row The row to check
     * @param col The column to check
     * @return true if the node is open and unioned
     */
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException("Row or column is out of bounds");
        }
        int contextNode = (row - 1) * gridSize + col;
        return unionTree.find(top) == unionTree.find(contextNode) && isOpen(row, col);
    }

    /**
     *
     * @return The number of open sites
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * Percolation occurs when the virtual top node is unioned with the virtual bottom node (i.e there is a path between
     * the top and the bottom)
     * @return true if it percolates
     */
    public boolean percolates() {
        return unionTree.find(top) == unionTree.find(bottom);
    }

}
