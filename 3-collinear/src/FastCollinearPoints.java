import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * Finds Collinear Points with a faster algorithm
 * For Coursera Algorithms course
 */
public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegments;

    /**
     * Finds the set of all line segments that contain 4 points
     * @param points The points to find colinear segments from
     */
    public FastCollinearPoints(Point[] points) {
        // Exception cases
        if (points == null) throw new IllegalArgumentException("Points array is null");
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("A point is null");
            }
        }
        if (repeated(points)) throw new IllegalArgumentException("A point is repeated");

        lineSegments = new ArrayList<>(1);

        // Sort into normal order
        Arrays.sort(points);
        Point[] pointsCopy = points.clone();


        for (int pIndex = 0; pIndex < points.length; pIndex++) {
            Point p = pointsCopy[pIndex];
            Arrays.sort(points);
            Arrays.sort(points, p.slopeOrder());


            Double currSlope = null;
            ArrayList<Point> collinearPoints = new ArrayList<>(1);
            collinearPoints.add(p);
            for (int qIndex = 1; qIndex < points.length; qIndex++) {
                Point q = points[qIndex];
                if (currSlope == null) currSlope = p.slopeTo(q);

                if (p.slopeTo(q) == currSlope) {
                    collinearPoints.add(q);

                    // The last element to check in the list
                    if (qIndex == points.length - 1 && collinearPoints.size() >= 3) {
                        Collections.sort(collinearPoints);
                        lineSegments.add(new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1)));
                    }
                } else {
                    if (collinearPoints.size() >= 3) {
                        Collections.sort(collinearPoints);
                        lineSegments.add(new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1)));
                    }

                    currSlope = p.slopeTo(q);
                    collinearPoints = new ArrayList<>(1);
                    collinearPoints.add(p);
                }
            }
        }
    }

    /**
     * Gets the number of line segments
     * @return The number of line segments in the points array
     */
    public int numberOfSegments() {
        return lineSegments.size();
    }

    /**
     * The line segments
     * @return An array of line segments
     */
    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[lineSegments.size()];
        ls = lineSegments.toArray(ls);
        return ls;
    }

    /**
     *
     * @param points Array of points
     * @return true if there are repeated points
     */
    private boolean repeated(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (j == i) continue;
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    /**
     * Testing suite
     * @param args text file with line 1 being the number of points and subsequent lines being points x y
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        // Print out the number of points
        StdOut.println("Num Segments:" + collinear.numberOfSegments());
    }
}
