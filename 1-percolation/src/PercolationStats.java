import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * From Coursera Algorithms Course: https://www.coursera.org/learn/algorithms-part1/
 * Simulation to check the threshold of percolation.
 * @author Jack Player
 */
public class PercolationStats {
    private final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] trialResults;

    /**
     * Monte Carlo Simulation to establish the threshold of percolation
     * @param n The n x n grid
     * @param trials Number of trials to run
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0");
        }

        this.trials = trials;
        this.trialResults = new double[trials];
        double totalNodes = n * n;
        Percolation percolation;

        for (int i = 0; i < trials;  i++) {
            percolation = new Percolation(n);
            double totalOpened = 0;

            while (!percolation.percolates()) {
                int randRow = StdRandom.uniform(n) + 1;
                int randCol = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(randRow, randCol)) {
                    percolation.open(randRow, randCol);
                    totalOpened++;
                }

            }
            double estimatedThreshold = totalOpened / totalNodes;

            this.trialResults[i] = estimatedThreshold;
        }
    }

    /**
     * Gets the average threshold from the trial results
     * @return The average trial result
     */
    public double mean() {
        return StdStats.mean(trialResults);
    }

    /**
     * Gets the standard deviation of trial results
     * @return The standard deviation of trial results
     */
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    /**
     * Gets the low 95% confidence interval of the trial results
     * @return The low confidence interval of trial results
     */
    public double confidenceLo() {
        return (StdStats.mean(trialResults) - ((CONFIDENCE_95 * Math.sqrt(stddev()))/Math.sqrt(trials)));
    }

    /**
     * Gets the high 95% confidence interval of the trial results
     * @return The high confidence interval of trial results
     */
    public double confidenceHi() {
        return (StdStats.mean(trialResults) + ((CONFIDENCE_95 * Math.sqrt(stddev()))/Math.sqrt(trials)));
    }

    /**
     * Test Client
     * @param args (1) The size n of n x n grid, (2) Number of trials
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats simulation = new PercolationStats(n, trials);

        System.out.println("mean" + "\t\t=" + " " + simulation.mean());
        System.out.println("stddev" + "\t\t=" + " " + simulation.stddev());
        System.out.println("95% confidence interval" + "\t\t=" + " " + "["+simulation.confidenceLo() + "," + simulation.confidenceHi() + "]");
    }
}
